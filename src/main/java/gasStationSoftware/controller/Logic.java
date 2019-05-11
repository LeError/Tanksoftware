package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.exceptions.NumberOutOfRangeException;
import gasStationSoftware.exceptions.OSException;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.ReadFile;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
import gasStationSoftware.util.WriteJSON;
import javafx.scene.control.TableView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Logic {

    private static Logic logic;
    private static WindowController windowController;

    private final String DATA_FILE_PATH = System.getProperty("user.home") + "\\TANKWare\\";
    private final String[] DATA_SUB_PATHS = {
            DATA_FILE_PATH + "receipts\\",
            DATA_FILE_PATH + "fuelOrders\\",
            DATA_FILE_PATH + "fuelDeliveries\\",
            DATA_FILE_PATH + "goodsOrders\\",
            DATA_FILE_PATH + "goodsDeliveries\\",
            DATA_FILE_PATH + "themes\\"
    };

    private final String[] DATA_FILE_NAMES = {
            "inventory.json",
            "settings.json",
            "themes\\default.json",
            "employees.txt"
    };

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<ItemType> types = new ArrayList<>();
    private ArrayList<FuelTank> tanks = new ArrayList<>();
    private ArrayList<GasPump> gasPumps = new ArrayList<>();

    private Logic() {
        checkDir(DATA_FILE_PATH);
        checkDirs(DATA_SUB_PATHS);
        try {
            checkDataFiles();
        } catch (IOException e) {
            displayError("Could not write file to filesystem !", e, true);
        } catch (DataFileNotFoundException e) {
            displayError("Could not find required data file!", e, true);
        }
    }

    public static Logic getInstance() {
        if (logic == null) {
            logic = new Logic();
        }
        return logic;
    }

    public static Logic getInstance(WindowController windowController) {
        Logic.windowController = windowController;
        return getInstance();
    }

    private void checkDir(String dir) {
        File dataDir = new File(dir);
        if (!dataDir.isDirectory()) {
            dataDir.mkdirs();
        }
    }

    private void checkDirs(String[] dirs) {
        for (String dir : dirs) {
            checkDir(dir);
        }
    }

    private void checkDataFiles() throws IOException, DataFileNotFoundException {
        for (String file : DATA_FILE_NAMES) {
            File dataFile = new File(DATA_FILE_PATH + file);
            if (!dataFile.exists() && !FilenameUtils.getExtension(file).equals("json")) {
                WriteFile write = new WriteFile(dataFile.toString());
                write.addLine("LASTUPDATE=" + Utility.getDate());
                write.addLine("EMPLOYEENR;FIRSTNAME;LASTNAME;EMPLOYMENTDATE");
                write.addLine("00000;ADMIN;ADMIN;" + Utility.getDate());
                write.write();
            } else if (!dataFile.exists() && FilenameUtils.getExtension(file).equals("json")) {
                switch (file) {
                case "inventory.json":
                    exportJSONResources("inventoryDefault.json", "inventory.json");
                    break;
                case "settings.json":
                    exportJSONResources("settings.json", "settings.json");
                    break;
                case "themes\\default.json":
                    exportJSONResources("defaultTheme.json", "themes\\default.json");
                    break;
                default:
                    throw new DataFileNotFoundException(file);
                }
            }
        }
    }

    private void exportJSONResources(String source, String file) {
        InputStream jsonSource = getClass().getClassLoader().getResourceAsStream("\\json\\" + source);
        File jsonDestination = new File(DATA_FILE_PATH + file);
        try {
            FileUtils.copyInputStreamToFile(jsonSource, jsonDestination);
        } catch (IOException e) {
            displayError("Could not export data file from jar!", e, true);
        }
    }

    public static void displayError(String error, Exception e, boolean end) {
        JOptionPane.showMessageDialog(null, error + "\n" + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        if(end){
            System.exit(-1);
        }
    }

    public void loadFiles() {
        try {
            loadTheme();
        } catch (DataFileNotFoundException e) {
            displayError("Can't load theme save file!", e, true);
        }
        try {
            loadEmployees();
        } catch (OSException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            loadInventory();
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberOutOfRangeException e) {
            e.printStackTrace();
        }
    }

    private void loadTheme() throws DataFileNotFoundException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[1]);
        String theme = read.getItemString("theme");
        windowController.setComboboxThemes(read.getItemStringArray("themes"), theme);
        read = new ReadJSON(DATA_SUB_PATHS[5] + theme + ".json");
        windowController.setTheme(
                Utility.hex2Rgb(read.getItemString("menuBar")),
                Utility.hex2Rgb(read.getItemString("contentPaneBackground")),
                Utility.hex2Rgb(read.getItemString("icons")),
                Utility.hex2Rgb(read.getItemString("dividerMenuBar")),
                Utility.hex2Rgb(read.getItemString("fontContent")),
                Utility.hex2Rgb(read.getItemString("buttonsBackground")),
                Utility.hex2Rgb(read.getItemString("buttonsFont")),
                Utility.hex2Rgb(read.getItemString("dividerContent"))
        );
    }

    private void loadEmployees() throws OSException, ParseException {
        ReadFile read = new ReadFile(DATA_FILE_PATH + DATA_FILE_NAMES[3]);
        String[][] lines = read.getLINES();
        for(int i = 0; i < lines.length; i++) {
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(lines[i][3]);
            employees.add(new Employee(Integer.parseInt(lines[i][0]), lines[i][1], lines[i][2], date));
        }
        for(Employee employee : employees) {
            windowController.addRowTEmployeesEmployeeOverview(employee);
        }
    }

    private void loadInventory() throws DataFileNotFoundException, NumberOutOfRangeException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[0]);

        createItemTypeObjects(read.getItemStringArray("itemLabel"), read.getItemStringArray("itemInventoryNumber"), read.getItemStringArray("itemType"));
        for(ItemType iType : this.types) {
            windowController.addRowTFuelsSettingsFuel(iType);
            windowController.addRowTGoodsSettingsGood(iType);
        }

        createFuelTankObjects(Utility.getIntArray(read.getItemStringArray("tankID")), Utility.getFloatArray(read.getItemStringArray("tankCapacity")), Utility.getFloatArray(read.getItemStringArray("tankLevel")), Utility.getIntArray(read.getItemStringArray("tankAssignedFuels")));
        for(FuelTank tank : tanks) {
            windowController.addRowTTanksSettingsTank(tank);
        }

        createGasPumpObjects(read.getItemStringArrayListArray("gasPumpAssignedTanks"));
        for (GasPump gasPump : this.gasPumps) {
            windowController.addRowTGasPumpsSettingsGasPump(gasPump);
        }
    }

    private void createItemTypeObjects(String[] label, String[] inventoryNumber, String[] type) {
        for(int i = 0; i < inventoryNumber.length; i++) {
            InventoryType invType = InventoryType.Good;
            switch(type[i]) {
                case "FUEL":
                    invType = InventoryType.Fuel;
                    break;
                default: invType = InventoryType.Good;
            }
            this.types.add(new ItemType(label[i], Integer.parseInt(inventoryNumber[i]), invType));
        }
        Collections.sort(this.types, Comparator.comparingInt(iType -> iType.getINVENTORY_NUMBER()));
    }

    private void createFuelTankObjects(int[] tankID, float[] tankCapacity, float[] tankLevel, int[] tankAssignedFuels) {
        ArrayList<ItemType> fuels = Utility.getInventoryType(types, InventoryType.Fuel);
        for(int i = 0; i < tankAssignedFuels.length; i++) {
            ItemType fuel = null;
            for(ItemType fuelType : fuels){
                if(tankAssignedFuels[i] == fuelType.getINVENTORY_NUMBER()) {
                    fuel = fuelType;
                }
            }
            try {
                tanks.add(new FuelTank(tankID[i], tankCapacity[i], tankLevel[i], fuel));
            } catch (NumberOutOfRangeException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(this.tanks, Comparator.comparingInt(tank -> tank.getTANK_NUMBER()));
    }

    private void createGasPumpObjects(ArrayList<String>[] gasPumps ) {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        for (int i = 0; i < gasPumps.length; i++) {
            ArrayList<FuelTank> tanks = new ArrayList<>();
            for (int j = 0; j < gasPumps[i].size(); j++) {
                for (int l = 0; l < this.tanks.size(); l++) {
                    if (this.tanks.get(l).getTANK_NUMBER() == Integer.parseInt(gasPumps[i].get(j)) &&
                            !tanks.contains(this.tanks.get(l))) {
                        tanks.add(this.tanks.get(l));
                    }
                }
            }
            this.gasPumps.add(new GasPump(i, tanks));
        }
        Collections.sort(this.gasPumps, Comparator.comparingInt(gasPump -> gasPump.getGAS_PUMP_NUMBER()));
    }

    public int getFreeInvNumber(InventoryType type) {
        int number = 1;
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        Collections.sort(types, Comparator.comparingInt(iType -> iType.getINVENTORY_NUMBER()));
        for (ItemType iType : types) {
            if (number != iType.getINVENTORY_NUMBER()) {
                break;
            }
            number++;
        }
        return number;
    }

    public int getFreeTankNumber() {
        int number = 1;
        Collections.sort(tanks, Comparator.comparingInt(tank -> tank.getTANK_NUMBER()));
        for (FuelTank tank : tanks) {
            if (number != tank.getTANK_NUMBER()) {
                break;
            }
            number++;
        }
        return number;
    }

    private int getFreeGasPumpNumber() {
        int number = 1;
        Collections.sort(gasPumps, Comparator.comparingInt(gasPump -> gasPump.getGAS_PUMP_NUMBER()));
        for (GasPump gasPump : gasPumps) {
            if (number != gasPump.getGAS_PUMP_NUMBER()) {
                break;
            }
            number++;
        }
        return number;
    }

    public void addItemType(String label, InventoryType type) {
        ItemType newItemType = new ItemType(label, getFreeInvNumber(type), type);
        types.add(newItemType);
        if (type == InventoryType.Fuel) {
            windowController.addRowTFuelsSettingsFuel(newItemType);
        } else if(type == InventoryType.Good) {
            windowController.addRowTGoodsSettingsGood(newItemType);
        }
        saveInventory();
    }

    public void addFuelTank(float capacity, float level, int fuel) {
        try {
            ArrayList<ItemType> fuels = Utility.getInventoryType(types, InventoryType.Fuel);
            FuelTank newFuelTank = new FuelTank(getFreeTankNumber(), capacity, level, fuels.get(fuel));
            tanks.add(newFuelTank);
            windowController.addRowTTanksSettingsTank(newFuelTank);
            saveInventory();
        } catch (NumberOutOfRangeException e) {
            e.printStackTrace();
        }
    }

    public void addGasPump(ArrayList<FuelTank> tanks) {
        GasPump newGasPump = new GasPump(getFreeGasPumpNumber(), tanks);
        gasPumps.add(newGasPump);
        windowController.addRowTGasPumpsSettingsGasPump(newGasPump);
        saveInventory();
    }

    private void saveInventory() {
        WriteJSON write = new WriteJSON(DATA_FILE_PATH + DATA_FILE_NAMES[0]);
        write.addItemArray("tankCapacity", getTankCapacity());
        write.addItemArray("tankLevel", getTankLevel());
        write.addItemArray("tankAssignedFuels", getTankAssignedFuels());
        write.addItemArray("tankID", getTankID());
        write.addItemArray("itemLabel", getItemLabel());
        write.addItemArray("itemInventoryNumber", getItemInventoryNumber());
        write.addItemArray("itemType", getItemType());
        write.addItemArrayListArray("gasPumpAssignedTanks", "gasPump", getGasPumpAssignedTanks());
        write.write(true);
    }

    private String[] getTankCapacity() {
        String[] tankCapacity = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankCapacity[i] = String.valueOf(tanks.get(i).getCAPACITY());
        }
        return tankCapacity;
    }

    private String[] getTankLevel() {
        String[] tankLevel = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankLevel[i] = String.valueOf(tanks.get(i).getLevel());
        }
        return tankLevel;
    }

    private String[] getTankAssignedFuels() {
        String[] tankAssignedFuels = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankAssignedFuels[i] = String.valueOf(tanks.get(i).getFuel().getINVENTORY_NUMBER());
        }
        return tankAssignedFuels;
    }

    private String[] getTankID() {
        String[] tankID = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankID[i] = String.valueOf(tanks.get(i).getTANK_NUMBER());
        }
        return tankID;
    }

    private String[] getItemLabel() {
        String[] itemLabel = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemLabel[i] = String.valueOf(types.get(i).getLABEL());
        }
        return itemLabel;
    }

    private String[] getItemInventoryNumber() {
        String[] itemInventoryNumber = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemInventoryNumber[i] = String.valueOf(types.get(i).getINVENTORY_NUMBER());
        }
        return itemInventoryNumber;
    }

    private String[] getItemType() {
        String[] itemType = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemType[i] = String.valueOf(types.get(i).getTYPE_LABEL());
        }
        return itemType;
    }

    private ArrayList<String>[] getGasPumpAssignedTanks() {
        ArrayList<String>[] gasPumpAssignedTanks = new ArrayList[gasPumps.size()];
        for (int i = 0; i < gasPumps.size(); i++) {
            gasPumpAssignedTanks[i] = new ArrayList<>();
            ArrayList<FuelTank> tanks = gasPumps.get(i).getTanks();
            for (int j = 0; j < tanks.size(); j++) {
                gasPumpAssignedTanks[i].add(String.valueOf(tanks.get(j).getTANK_NUMBER()));
            }
        }
        return gasPumpAssignedTanks;
    }

    public ArrayList<String> getFuel() {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        ArrayList<String> fuel = new ArrayList<>();
        for (ItemType type : types) {
            fuel.add(type.getINVENTORY_NUMBER() + ": " + type.getLABEL());
        }
        return fuel;
    }

    public void addTankTableRows(TableView table) {
        for(FuelTank tank : tanks) {
            table.getItems().add(tank);
        }
    }
}