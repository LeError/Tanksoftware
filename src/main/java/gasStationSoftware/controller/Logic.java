package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.exceptions.NumberOutOfRangeException;
import gasStationSoftware.exceptions.OSException;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.CompareItemType;
import gasStationSoftware.util.ReadFile;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
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

    private Employee[] employees;
    private ItemType[] types;
    private FuelTank[] tanks;
    private GasPump[] gasPumps;

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
        employees = new Employee[lines.length];
        for(int i = 0; i < employees.length; i++) {
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(lines[i][3]);
            employees[i] = new Employee(Integer.parseInt(lines[i][0]), lines[i][1], lines[i][2], date);
        }
        for(Employee employee : employees) {
            windowController.addRowTEmployeesEmployeeOverview(employee);
        }
    }

    private void loadInventory() throws DataFileNotFoundException, NumberOutOfRangeException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[0]);
        String[] label = read.getItemStringArray("itemLabel");
        String[] inventoryNumber = read.getItemStringArray("itemInventoryNumber");
        String[] type = read.getItemStringArray("itemType");
        this.types = new ItemType[inventoryNumber.length];
        for(int i = 0; i < inventoryNumber.length; i++) {
            InventoryType invType = InventoryType.Good;
            switch(type[i]) {
                case "FUEL":
                    invType = InventoryType.Fuel;
                    break;
                default: invType = InventoryType.Good;
            }
            this.types[i] = new ItemType(label[i], Integer.parseInt(inventoryNumber[i]), invType);
        }
        for(ItemType iType : this.types) {
            windowController.addRowTFuelsSettingsFuel(iType);
            windowController.addRowTGoodsSettingsGood(iType);
        }
        int[] tankID = Utility.getIntArray(read.getItemStringArray("tankID"));
        float[] tankCapacity = Utility.getFloatArray(read.getItemStringArray("tankCapacity"));
        float[] tankLevel = Utility.getFloatArray(read.getItemStringArray("tankLevel"));
        int[] tankAssignedFuels = Utility.getIntArray(read.getItemStringArray("tankAssignedFuels"));
        ArrayList<ItemType> fuels = Utility.getInventoryType(types, InventoryType.Fuel);
        tanks = new FuelTank[tankAssignedFuels.length];
        for(int i = 0; i < tankAssignedFuels.length; i++) {
            ItemType fuel = null;
            for(ItemType fuelType : fuels){
                if(tankAssignedFuels[i] == fuelType.getINVENTORY_NUMBER()) {
                    fuel = fuelType;
                }
            }
            tanks[i] = new FuelTank(tankID[i], tankCapacity[i], tankLevel[i], fuel);
        }
        for(FuelTank tank : tanks) {
            windowController.addRowTTanksSettingsTank(tank);
        }
        ArrayList<String>[] gasPumps = read.getItemStringArrayListArray("gasPumpAssignedTanks");
        this.gasPumps = new GasPump[gasPumps.length];
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        for (int i = 0; i < gasPumps.length; i++) {
            ArrayList<FuelTank> tanks = new ArrayList<>();
            for (int j = 0; j < gasPumps[i].size(); j++) {
                for (int l = 0; l < this.tanks.length; l++) {
                    if (this.tanks[l].getTANK_NUMBER() == Integer.parseInt(gasPumps[i].get(j)) &&
                    !tanks.contains(this.tanks[l])) {
                        tanks.add(this.tanks[l]);
                    }
                }
            }
            ArrayList<ItemType> fuelTypes = new ArrayList<>();
            for (int j = 0; j < tanks.size(); j++) {
                for (int l = 0; l < types.size(); l++) {
                    if (types.get(l).getINVENTORY_NUMBER() == tanks.get(j).getFuel().getINVENTORY_NUMBER() &&
                    !fuelTypes.contains(types.get(l))) {
                        fuelTypes.add(types.get(l));
                    }
                }
            }
            this.gasPumps[i] = new GasPump(fuels, i, tanks);
        }
        for (GasPump gasPump : this.gasPumps) {
            //TODO add row
        }
    }

    public int getFreeInvNumber(InventoryType type) {
        int number = 1;
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        Collections.sort(types, new CompareItemType());
        for(int i = 0; i < types.size(); i++) {
            if(number != types.get(i).getINVENTORY_NUMBER()) {
                break;
            }
            number++;
        }
        return number;
    }

    public void addItemType(String label, InventoryType type) { //TODO make comp with GOOD
        ItemType newItemType = new ItemType(label, getFreeInvNumber(type), type);
        ItemType[] oldTypes = types;
        types = new ItemType[oldTypes.length + 1];
        for(int i = 0; i < oldTypes.length; i++) {
            types[i] = oldTypes[i];
        }
        types[types.length - 1] = newItemType;
        windowController.addRowTFuelsSettingsFuel(newItemType);
    }
}