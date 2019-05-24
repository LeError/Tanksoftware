package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.exceptions.NumberOutOfRangeException;
import gasStationSoftware.exceptions.OSException;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.Fuel;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.Good;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.models.StorageUnit;
import gasStationSoftware.ui.ErrorDialog;
import gasStationSoftware.util.ReadFile;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
import gasStationSoftware.util.WriteJSON;
import javafx.scene.control.TableView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
    private ArrayList<StorageUnit> storageUnits = new ArrayList<>();
    private ArrayList<Fuel> fuels = new ArrayList<>();
    private ArrayList<Good> goods = new ArrayList<>();

    //===[CONSTRUCTOR]==================================================

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

    //===[INSTANCE GETTER]==================================================

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

    //===[CHECK DIRS AND FILES]==================================================

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

    //===[JSON EXTRACTION]==================================================

    private void exportJSONResources(String source, String file) {
        InputStream jsonSource = getClass().getClassLoader().getResourceAsStream("\\json\\" + source);
        File jsonDestination = new File(DATA_FILE_PATH + file);
        try {
            FileUtils.copyInputStreamToFile(jsonSource, jsonDestination);
        } catch (IOException e) {
            displayError("Could not export data file from jar!", e, true);
        }
    }

    //===[DISPLAY ERRORS]==================================================

    public static void displayError(String error, Exception e, boolean end) { // TODO change to errorDialog
        new ErrorDialog(windowController.getRootPane(), error, e, end);
        if(end){
            System.exit(-1);
        }
    }

    //===[LOAD SAVE FILES]==================================================

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

        createStorageUnitObjects(read.getItemStringArray("storageUnitLabel"), Utility.getIntArray(read.getItemStringArray("storageUnitX")), Utility.getIntArray(read.getItemStringArray("storageUnitY")));
        for(StorageUnit storageUnit : storageUnits) {
            windowController.addRowTSettingsStorageUnit(storageUnit);
        }

        createFuel(Utility.getIntArray(read.getItemStringArray("fuelType")),
        Utility.getFloatArray(read.getItemStringArray("fuelPrice")), read.getItemStringArray("fuelCurrency"),
        Utility.getFloatArray(read.getItemStringArray("fuelAmount")));
        for (Fuel fuel : fuels) {
            windowController.addRowTFuelsFuelOverview(fuel);
        }

        createGood(Utility.getIntArray(read.getItemStringArray("goodType")),
        Utility.getFloatArray(read.getItemStringArray("goodPrice")),
        read.getItemStringArray("goodCurrency"),
        Utility.getIntArray(read.getItemStringArray("goodAmount")),
        read.getItemStringArray("goodStorageUnit"));
        for(Good good : goods) {
            windowController.addRowTGoodsInventoryOverview(good);
        }
    }

    //===[CREATE OBJECTS FROM JSON]==================================================

    private void createItemTypeObjects(String[] label, String[] inventoryNumber, String[] type) {
        for(int i = 0; i < inventoryNumber.length; i++) {
            InventoryType invType;
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

    private void createStorageUnitObjects(String[] label, int[] x, int[] y) {
       for(int i = 0; i < label.length; i++) {
           storageUnits.add(new StorageUnit(label[i], x[i] ,y[i]));
       }
    }

    private void createFuel(int[] invNumber, float[] price, String[] currency, float[] amount) {
        ArrayList<ItemType> fuelTypes = Utility.getInventoryType(types, InventoryType.Fuel);
        for (int i = 0; i < invNumber.length; i++) {
            ItemType fuel = null;
            for (ItemType fuelType : fuelTypes) {
                if (invNumber[i] == fuelType.getINVENTORY_NUMBER()) {
                    fuel = fuelType;
                    break;
                }
            }
            fuels.add(new Fuel(fuel, price[i], currency[i], amount[i]));
        }
    }

    private void createGood(int[] invNumber, float[] price, String[] currency, int[] amount, String[] storageUnits) {
        ArrayList<ItemType> goodTypes = Utility.getInventoryType(types, InventoryType.Good);
        for (int i = 0; i < invNumber.length; i++) {
            ItemType good = null;
            for (ItemType goodType : goodTypes) {
                if (invNumber[i] == goodType.getINVENTORY_NUMBER()) {
                    good = goodType;
                    break;
                }
            }
            StorageUnit storage = null;
            for(int y = 0; y < getStorageUnit().size(); y++) {
                if(getStorageUnit().get(y).equals(storageUnits[i])) {
                    storage = this.storageUnits.get(y);
                }
            }
            goods.add(new Good(good, price[i], currency[i], storage, amount[i]));
        }
    }

    //===[GET FREE IDS]==================================================

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

    //===[ADD NEW OBJECT]==================================================

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

    public void addStorageUnit(String label, int x, int y) {
        StorageUnit newStorageUnit = new StorageUnit(label, x, y);
        storageUnits.add(newStorageUnit);
        windowController.addRowTSettingsStorageUnit(newStorageUnit);
        saveInventory();
    }

    public void addFuel(ItemType iType, float amount, float price, String currency) {
        boolean newEntry = true;
        for(Fuel fuel : fuels) {
            if(fuel.getTYPE() == iType) {
                newEntry = false;
            }
        }

        if(newEntry) {
            Fuel newFuel = new Fuel(iType, price, currency, amount);
            fuels.add(newFuel);
            windowController.addRowTFuelsFuelOverview(newFuel);
        } else {
            displayError("Kraftstoff exsistiert bereits", new Exception("duplicate entry"), false);
        }
        saveInventory();
    }

    public void addGood(ItemType iType, int amount, float price, String currency, String storageUnit) {
        boolean newEntry = true;
        for(Good good : goods) {
            if(good.getTYPE() == iType) {
                newEntry = false;
            }
        }

        int idxStorage = 0;
        ArrayList<String> storages = getStorageUnit();
        for(int i = 0; i < storages.size(); i++) {
            if(storages.get(i).equals(storageUnit)) {
                idxStorage = i;
            }
        }

        if(newEntry) {
            Good newGood = new Good(iType, price, currency, storageUnits.get(idxStorage), amount);
            goods.add(newGood);
            windowController.addRowTGoodsInventoryOverview(newGood);
        } else {
            displayError("Kraftstoff exsistiert bereits", new Exception("duplicate entry"), false);
        }
        saveInventory();
    }

    public void importFile(String path, int dir, String theme)
    throws IOException {
        String file = "";
        String extension = "";
        switch (dir) {
        case 0:
            file = "RECEIPT_";
            extension = ".txt";
            break;
        case 1:
            file = "FUEL_ORDER_";
            extension = ".txt";
            break;
        case 2:
            file = "FUEL_DELIVERY_";
            extension = ".txt";
            break;
        case 3:
            file = "GOOD_ORDER_";
            extension = ".txt";
            break;
        case 4:
            file = "GOOD_DELIVERY_";
            extension = ".txt";
            break;
        case 5:
            file = theme;
            extension = ".json";
            break;
        default:
            throw new IOException();
        }
        int number = new File(DATA_SUB_PATHS[dir]).listFiles().length;
        Files.copy(new File(path).toPath(), new File(DATA_SUB_PATHS[2] + file + number + extension).toPath());
    }

    //===[SAVE FILES]==================================================

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
        write.addItemArray("storageUnitLabel", getStorageUnitLabel());
        write.addItemArray("storageUnitX", getStorageUnitX());
        write.addItemArray("storageUnitY", getStorageUnitY());
        write.addItemArray("fuelType", getInvNumberFuel());
        write.addItemArray("fuelPrice", getPriceFuel());
        write.addItemArray("fuelCurrency", getCurrencyFuel());
        write.addItemArray("fuelAmount", getAmountFuel());
        write.addItemArray("goodType", getInvNumberGood());
        write.addItemArray("goodPrice", getPriceGood());
        write.addItemArray("goodCurrency", getCurrencyGood());
        write.addItemArray("goodAmount", getAmountGood());
        write.addItemArray("goodStorageUnit", getStorageUnitGood());
        write.write(true);
    }

    //===[GET STRINGS FOR JSON]==================================================

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

    private String[] getStorageUnitLabel() {
        String[] label = new String[storageUnits.size()];
        for(int i = 0; i < label.length; i++) {
            label[i] = storageUnits.get(i).getLabel();
        }
        return label;
    }

    private String[] getStorageUnitX() {
        String[] x = new String[storageUnits.size()];
        for(int i = 0; i < x.length; i++) {
            x[i] = String.valueOf(storageUnits.get(i).getX());
        }
        return x;
    }

    private String[] getStorageUnitY() {
        String[] y = new String[storageUnits.size()];
        for(int i = 0; i < y.length; i++) {
            y[i] = String.valueOf(storageUnits.get(i).getY());
        }
        return y;
    }

    private String[] getInvNumberFuel() {
        String[] invNum = new String[fuels.size()];
        for (int i = 0; i < invNum.length; i++) {
            invNum[i] = String.valueOf(fuels.get(i).getINVENTORY_NUMBER());
        }
        return invNum;
    }

    private String[] getPriceFuel() {
        String[] price = new String[fuels.size()];
        for (int i = 0; i < price.length; i++) {
            price[i] = String.valueOf(fuels.get(i).getPrice());
        }
        return price;
    }

    private String[] getCurrencyFuel() {
        String[] currency = new String[fuels.size()];
        for (int i = 0; i < currency.length; i++) {
            currency[i] = fuels.get(i).getCurrency();
        }
        return currency;
    }

    private String[] getAmountFuel() {
        String[] amount = new String[fuels.size()];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = String.valueOf(fuels.get(i).getAmount());
        }
        return amount;
    }

    private String[] getInvNumberGood() {
        String[] invNum = new String[fuels.size()];
        for (int i = 0; i < invNum.length; i++) {
            invNum[i] = String.valueOf(fuels.get(i).getINVENTORY_NUMBER());
        }
        return invNum;
    }

    private String[] getPriceGood() {
        String[] price = new String[fuels.size()];
        for (int i = 0; i < price.length; i++) {
            price[i] = String.valueOf(fuels.get(i).getPrice());
        }
        return price;
    }

    private String[] getCurrencyGood() {
        String[] currency = new String[fuels.size()];
        for (int i = 0; i < currency.length; i++) {
            currency[i] = fuels.get(i).getCurrency();
        }
        return currency;
    }

    private String[] getAmountGood() {
        String[] amount = new String[fuels.size()];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = String.valueOf(fuels.get(i).getAmount());
        }
        return amount;
    }

    private String[] getStorageUnitGood() {
        String[] storage = new String[fuels.size()];
        for(int i = 0; i < storage.length; i++) {
            storage[i] = storageUnits.get(i).getLabel() + " (" + storageUnits.get(i).getX() + "|" + storageUnits.get(i).getY() + ")";
        }
        return storage;
    }

    //===[GETTER]==================================================

    public ArrayList<String> getFuel() {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        ArrayList<String> fuel = new ArrayList<>();
        for (ItemType type : types) {
            fuel.add(type.getINVENTORY_NUMBER() + ": " + type.getLABEL());
        }
        return fuel;
    }

    public ArrayList<ItemType> getItemTypes(InventoryType type) {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        return types;
    }

    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    public ArrayList<String> getStorageUnit() {
        ArrayList<String> storage = new ArrayList<>();
        for(StorageUnit storageUnit : storageUnits) {
            storage.add(storageUnit.getLabel() + " (" + storageUnit.getX() + "|" + storageUnit.getY() + ")");
        }
        return storage;
    }

    //===[GET ROWS FOR INPUT DIALOGS]==================================================

    public void addTankTableRows(TableView table) {
        for(FuelTank tank : tanks) {
            table.getItems().add(tank);
        }
    }
}