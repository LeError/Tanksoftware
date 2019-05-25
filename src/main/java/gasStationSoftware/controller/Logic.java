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

    /**
     * Constructor für Logic
     * @author Robin Herder
     */
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

    /**
     * Getter für die Logic Instance
     * @return logic
     * @author Robin Herder
     */
    public static Logic getInstance() {
        if (logic == null) {
            logic = new Logic();
        }
        return logic;
    }

    /**
     * Getter für die Logic Instance
     * @param windowController
     * @return logic
     * @author Robin Herder
     */
    public static Logic getInstance(WindowController windowController) {
        Logic.windowController = windowController;
        return getInstance();
    }

    //===[CHECK DIRS AND FILES]==================================================

    /**
     * Directory erstellen
     * @param dir Dateipfad als String
     * @author Robin Herder
     */
    private void checkDir(String dir) {
        File dataDir = new File(dir);
        if (!dataDir.isDirectory()) {
            dataDir.mkdirs();
        }
    }

    /**
     * Erstellt mehrere Directories
     * @param dirs Array mit Dateipfaden
     * @author Robin Herder
     */
    private void checkDirs(String[] dirs) {
        for (String dir : dirs) {
            checkDir(dir);
        }
    }

    /**
     * @throws IOException
     * @throws DataFileNotFoundException
     * @author Robin Herder
     */
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

    /**
     * @param source
     * @param file
     * @author Robin Herder
     */
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

    /**
     * Zeigt Errors an
     * @param error
     * @param e
     * @param end
     * @author Robin Herder
     */
    public static void displayError(String error, Exception e, boolean end) { // TODO change to errorDialog
        new ErrorDialog(windowController.getRootPane(), error, e, end);
        if(end){
            System.exit(-1);
        }
    }

    //===[LOAD SAVE FILES]==================================================

    /**
     * Lädt Dateien
     * @author Robin Herder
     */
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

    /**
     * Lädt Theme
     * @throws DataFileNotFoundException
     * @author Robin Herder
     */
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

    /**
     * Lädt Angestellte
     * @throws OSException
     * @throws ParseException
     * @author Robin Herder
     */
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

    /**
     * Lädt Inventar
     * @throws DataFileNotFoundException
     * @throws NumberOutOfRangeException
     * @author Robin Herder
     */
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
        read.getItemStringArray("goodStorageUnit"),
        read.getItemStringArray("goodUnit"));
        for(Good good : goods) {
            windowController.addRowTGoodsInventoryOverview(good);
        }
    }

    //===[CREATE OBJECTS FROM JSON]==================================================

    /**
     * Erstellt Item-Objekte aus der JSON
     * @param label
     * @param inventoryNumber
     * @param type
     * @author Robin Herder
     */
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

    /**
     * Erstellt Tanks
     * @param tankID
     * @param tankCapacity
     * @param tankLevel
     * @param tankAssignedFuels
     * @author Robin Herder
     */
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

    /**
     * Erstellt Zapfsäulen
     * @param gasPumps
     * @author Robin Herder
     */
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

    /**
     * Erstellt Lager
     * @param label
     * @param x
     * @param y
     * @author Robin Herder
     */
    private void createStorageUnitObjects(String[] label, int[] x, int[] y) {
       for(int i = 0; i < label.length; i++) {
           storageUnits.add(new StorageUnit(label[i], x[i] ,y[i]));
       }
    }

    /**
     * Erstellt Kraftstoff
     * @param invNumber
     * @param price
     * @param currency
     * @param amount
     * @author Robin Herder
     */
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

    /**
     * Erstellt Ware
     * @param invNumber
     * @param price
     * @param currency
     * @param amount
     * @param storageUnits
     * @author Robin Herder
     */
    private void createGood(int[] invNumber, float[] price, String[] currency, int[] amount, String[] storageUnits, String[] unit) {
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
            goods.add(new Good(good, price[i], currency[i], storage, amount[i], unit[i]));
        }
    }

    //===[GET FREE IDS]==================================================

    /**
     * Gibt die nächste freie Inventarnummer zurück
     * @param type
     * @return freeInvNumber
     * @author Robin Herder
     */
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

    /**
     * Gibt nächste freie Tanknummer zurück
     * @return FreeTankNumber
     * @author Robin Herder
     */
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

    /**
     * Gibt nächste freie Zapfsäulennummer zurück
     * @return FreeGasPumpNumber
     * @author Robin Herder
     */
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

    /**
     * Neues Item hinzufügen
     * @param label
     * @param type
     * @author Robin Herder
     */
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

    /**
     * Neuen Tank hinzufügen
     * @param capacity
     * @param level
     * @param fuel
     * @author Robin Herder
     */
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

    /**
     * Neue Zapfsäule hinzufügen
     * @param tanks
     * @author Robin Herder
     */
    public void addGasPump(ArrayList<FuelTank> tanks) {
        GasPump newGasPump = new GasPump(getFreeGasPumpNumber(), tanks);
        gasPumps.add(newGasPump);
        windowController.addRowTGasPumpsSettingsGasPump(newGasPump);
        saveInventory();
    }

    /**
     * Neues Lager hinzufügen
     * @param label
     * @param x
     * @param y
     * @author Robin Herder
     */
    public void addStorageUnit(String label, int x, int y) {
        StorageUnit newStorageUnit = new StorageUnit(label, x, y);
        storageUnits.add(newStorageUnit);
        windowController.addRowTSettingsStorageUnit(newStorageUnit);
        saveInventory();
    }

    /**
     * Neuen Kraftstoff hinzufügen
     * @param iType
     * @param amount
     * @param price
     * @param currency
     * @author Robin Herder
     */
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

    /**
     * Neue Ware hinzufügen
     * @param iType
     * @param amount
     * @param price
     * @param currency
     * @param storageUnit
     * @param unit
     * @author Robin Herder
     */
    public void addGood(ItemType iType, int amount, float price, String currency, String storageUnit, String unit) {
        boolean newEntry = true;
        for(Good good : goods) {
            if(good.getTYPE() == iType && good.getUNIT().equals(unit)) {
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
            Good newGood = new Good(iType, price, currency, storageUnits.get(idxStorage), amount, unit);
            goods.add(newGood);
            windowController.addRowTGoodsInventoryOverview(newGood);
        } else {
            displayError("Produkt exsistiert bereits", new Exception("duplicate entry"), false);
        }
        saveInventory();
    }

    /**
     * @param path
     * @param dir
     * @param theme
     * @throws IOException
     * @author Robin Herder
     */
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

    /**
     * Inventar in JSON speichern
     * @author Robin Herder
     */
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
        write.addItemArray("goodUnit", getUnit());
        write.write(true);
    }

    //===[GET STRINGS FOR JSON]==================================================

    /**
     * Gibt Tankkapazität aller Tanks zurück
     * @return tankCapacity[]
     * @author Robin Herder
     */
    private String[] getTankCapacity() {
        String[] tankCapacity = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankCapacity[i] = String.valueOf(tanks.get(i).getCAPACITY());
        }
        return tankCapacity;
    }

    /**
     * Gibt Füllstand aller Tanks zurück
     * @return tankLevel[]
     * @author Robin Herder
     */
    private String[] getTankLevel() {
        String[] tankLevel = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankLevel[i] = String.valueOf(tanks.get(i).getLevel());
        }
        return tankLevel;
    }

    /**
     * Gibt die Kraftstoffarten der Tanks zurück
     * @return tankAssignedFuels[]
     * @author Robin Herder
     */
    private String[] getTankAssignedFuels() {
        String[] tankAssignedFuels = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankAssignedFuels[i] = String.valueOf(tanks.get(i).getFuel().getINVENTORY_NUMBER());
        }
        return tankAssignedFuels;
    }

    /**
     * Gibt die ID des Tanks zurück
     * @return tankID[]
     * @author Robin Herder
     */
    private String[] getTankID() {
        String[] tankID = new String[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            tankID[i] = String.valueOf(tanks.get(i).getTANK_NUMBER());
        }
        return tankID;
    }

    /**
     * Gibt die Bezeichnung aller Items zurück
     * @return itemLabel[]
     * @author Robin Herder
     */
    private String[] getItemLabel() {
        String[] itemLabel = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemLabel[i] = String.valueOf(types.get(i).getLABEL());
        }
        return itemLabel;
    }

    /**
     * Gibt die Inventarnummer aller Items zurück
     * @return itemInventoryNumber[]
     * @author Robin Herder
     */
    private String[] getItemInventoryNumber() {
        String[] itemInventoryNumber = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemInventoryNumber[i] = String.valueOf(types.get(i).getINVENTORY_NUMBER());
        }
        return itemInventoryNumber;
    }

    /**
     * Gibt den Itemtyp aller Items zurück
     * @return itemType[]
     * @author Robin Herder
     */
    private String[] getItemType() {
        String[] itemType = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            itemType[i] = String.valueOf(types.get(i).getTYPE_LABEL());
        }
        return itemType;
    }

    /**
     * Gibt die Tanks einer Zapfsäule zurück
     * @return gasPumpAssignedTanks[]
     * @author Robin Herder
     */
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

    /**
     * Gibt den Namen aller Lager zurück
     * @return
     * @author Robin Herder
     */
    private String[] getStorageUnitLabel() {
        String[] label = new String[storageUnits.size()];
        for(int i = 0; i < label.length; i++) {
            label[i] = storageUnits.get(i).getLabel();
        }
        return label;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    private String[] getStorageUnitX() {
        String[] x = new String[storageUnits.size()];
        for(int i = 0; i < x.length; i++) {
            x[i] = String.valueOf(storageUnits.get(i).getX());
        }
        return x;
    }

    /**
     * @return
     * @author Robin Herder
     */
    private String[] getStorageUnitY() {
        String[] y = new String[storageUnits.size()];
        for(int i = 0; i < y.length; i++) {
            y[i] = String.valueOf(storageUnits.get(i).getY());
        }
        return y;
    }

    /**
     * Gibt die Inventarnummer aller Kraftstoffe zurück
     * @return invNum[]
     * @author Robin Herder
     */
    private String[] getInvNumberFuel() {
        String[] invNum = new String[fuels.size()];
        for (int i = 0; i < invNum.length; i++) {
            invNum[i] = String.valueOf(fuels.get(i).getINVENTORY_NUMBER());
        }
        return invNum;
    }

    /**
     * Gibt den Preis aller Kraftstoffe zurück
     * @return price[]
     * @author Robin Herder
     */
    private String[] getPriceFuel() {
        String[] price = new String[fuels.size()];
        for (int i = 0; i < price.length; i++) {
            price[i] = String.valueOf(fuels.get(i).getPrice());
        }
        return price;
    }

    /**
     *
     * @return currency[]
     * @author Robin Herder
     */
    private String[] getCurrencyFuel() {
        String[] currency = new String[fuels.size()];
        for (int i = 0; i < currency.length; i++) {
            currency[i] = fuels.get(i).getCurrency();
        }
        return currency;
    }

    /**
     * Gibt die Menge an Kraftstoff zurück
     * @return amount[]
     * @author Robin Herder
     */
    private String[] getAmountFuel() {
        String[] amount = new String[fuels.size()];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = String.valueOf(fuels.get(i).getAmount());
        }
        return amount;
    }

    /**
     * Gibt die Inventarnummer aller Waren zurück
     * @return
     * @author Robin Herder
     */
    private String[] getInvNumberGood() {
        String[] invNum = new String[goods.size()];
        for (int i = 0; i < invNum.length; i++) {
            invNum[i] = String.valueOf(goods.get(i).getINVENTORY_NUMBER());
        }
        return invNum;
    }

    /**
     * Gibt den Preis aller Waren zurück
     * @return price[]
     * @author Robin Herder
     */
    private String[] getPriceGood() {
        String[] price = new String[goods.size()];
        for (int i = 0; i < price.length; i++) {
            price[i] = String.valueOf(goods.get(i).getPrice());
        }
        return price;
    }

    /**
     *
     * @return currency[]
     * @author Robin Herder
     */
    private String[] getCurrencyGood() {
        String[] currency = new String[goods.size()];
        for (int i = 0; i < currency.length; i++) {
            currency[i] = goods.get(i).getCurrency();
        }
        return currency;
    }

    /**
     * Gibt die menge aller Waren zurück
     * @return amount[]
     * @author Robin Herder
     */
    private String[] getAmountGood() {
        String[] amount = new String[goods.size()];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = String.valueOf(goods.get(i).getAmount());
        }
        return amount;
    }

    /**
     *
     * @return storage[]
     * @author Robin Herder
     */
    private String[] getStorageUnitGood() {
        String[] storage = new String[goods.size()];
        for(int i = 0; i < storage.length; i++) {
            storage[i] = goods.get(i).getStorage().getLabel() + " (" + goods.get(i).getStorage().getX() + "|" + goods.get(i).getStorage().getY() + ")";
        }
        return storage;
    }

    /**
     * @return unit[]
     */
    private String[] getUnit() {
        String[] unit = new String[goods.size()];
        for(int i = 0; i < goods.size(); i++) {
            unit[i] = goods.get(i).getUNIT();
        }
        return unit;
    }

    //===[GETTER]==================================================

    /**
     * Gibt alle Kraftstoffe zurück
     * @return fuel
     * @author Robin Herder
     */
    public ArrayList<String> getFuel() {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        ArrayList<String> fuel = new ArrayList<>();
        for (ItemType type : types) {
            fuel.add(type.getINVENTORY_NUMBER() + ": " + type.getLABEL());
        }
        return fuel;
    }

    /**
     * Gibt alle Itemtypes zurück
     * @param type
     * @return types[]
     * @author Robin Herder
     */
    public ArrayList<ItemType> getItemTypes(InventoryType type) {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        return types;
    }

    /**
     * Gibt alle Tanks zurpck
     * @return
     * @author Robin Herder
     */
    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    /**
     *
     * @return storage[]
     * @author Robin Herder
     */
    public ArrayList<String> getStorageUnit() {
        ArrayList<String> storage = new ArrayList<>();
        for(StorageUnit storageUnit : storageUnits) {
            storage.add(storageUnit.getLabel() + " (" + storageUnit.getX() + "|" + storageUnit.getY() + ")");
        }
        return storage;
    }

    //===[GET ROWS FOR INPUT DIALOGS]==================================================

    /**
     *
     * @param table
     * @author Robin Herder
     */
    public void addTankTableRows(TableView table) {
        for(FuelTank tank : tanks) {
            table.getItems().add(tank);
        }
    }
}