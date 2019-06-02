package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.exceptions.NumberOutOfRangeException;
import gasStationSoftware.models.*;
import gasStationSoftware.ui.ErrorDialog;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.ReadListFile;
import gasStationSoftware.util.ReadTableFile;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
import gasStationSoftware.util.WriteJSON;
import javafx.scene.control.TableView;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
            "employees.txt",
            "themes\\dark.json",
            "users.json",
            "tankSimulation.json"
    };

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<ItemType> types = new ArrayList<>();
    private ArrayList<FuelTank> tanks = new ArrayList<>();
    private ArrayList<GasPump> gasPumps = new ArrayList<>();
    private ArrayList<Fuel> fuels = new ArrayList<>();
    private ArrayList<Good> goods = new ArrayList<>();
    private ArrayList<Document> documents = new ArrayList<>();
    private ArrayList<CustomerOrder> receipts = new ArrayList<>();

    private Employee activeEmployee;

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
                case "users.json":
                    exportJSONResources("users.json", "users.json");
                    break;
                case "themes\\dark.json":
                    exportJSONResources("darkTheme.json", "themes\\dark.json");
                    break;
                case "tankSimulation.json":
                    exportJSONResources("tankSimulation.json", "tankSimulation.json");
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
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadInventory();
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        }
        loadFuelDeliveries();
        loadGoodDeliveries();
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
     * @throws ParseException
     * @author Robin Herder
     */
    private void loadEmployees() throws ParseException, DataFileNotFoundException {
        ReadTableFile read = new ReadTableFile(DATA_FILE_PATH + DATA_FILE_NAMES[3]);
        String[][] lines = read.getLINES();
        ReadJSON readJSON = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[5]);
        String[] ids = readJSON.getItemStringArray("userID");
        String[] passes = readJSON.getItemStringArray("userPass");
        String[] roles = readJSON.getItemStringArray("userRole");
        for(int i = 0; i < lines.length; i++) {
            int roleID = -1;
            String pass = "";
            for(int ii = 0; ii < ids.length; ii++) {
                if(Integer.parseInt(lines[i][0]) == Integer.parseInt(ids[ii])){
                    roleID = Integer.parseInt(roles[ii]);
                    pass = passes[ii];
                }
            }
            UserRole role = null;
            switch(roleID) {
                case 0:
                    role = UserRole.admin;
                    break;
                case 1:
                    role = UserRole.employee;
                    break;
                default: role = UserRole.assistant;
            }
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(lines[i][3]);
            employees.add(new Employee(Integer.parseInt(lines[i][0]), lines[i][1], lines[i][2], date, role, pass));
        }
        Collections.sort(employees, Comparator.comparingInt(employee -> employee.getEMPLOYEE_NUMBER()));
        windowController.addRowTEmployeesEmployeeOverview(employees);
    }

    /**
     * Lädt Inventar
     * @throws DataFileNotFoundException
     * @throws NumberOutOfRangeException
     * @author Robin Herder
     */
    private void loadInventory() throws DataFileNotFoundException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[0]);

        createItemTypeObjects(read.getItemStringArray("itemLabel"), read.getItemStringArray("itemInventoryNumber"), read.getItemStringArray("itemType"));
        windowController.addRowTFuelsSettingsFuel(types);
        windowController.addRowTGoodsSettingsGood(types);

        createFuelTankObjects(Utility.getIntArray(read.getItemStringArray("tankID")), Utility.getFloatArray(read.getItemStringArray("tankCapacity")), Utility.getFloatArray(read.getItemStringArray("tankLevel")), Utility.getIntArray(read.getItemStringArray("tankAssignedFuels")));
        windowController.addRowTTanksSettingsTank(tanks);

        createGasPumpObjects(read.getItemStringArrayListArray("gasPumpAssignedTanks"));
        windowController.addRowTGasPumpsSettingsGasPump(gasPumps);

        createFuel(Utility.getIntArray(read.getItemStringArray("fuelType")),
        Utility.getFloatArray(read.getItemStringArray("fuelPrice")), read.getItemStringArray("fuelCurrency"),
        Utility.getFloatArray(read.getItemStringArray("fuelAmount")));
        windowController.addRowTFuelsFuelOverview(fuels);

        createGood(Utility.getIntArray(read.getItemStringArray("goodType")),
        Utility.getFloatArray(read.getItemStringArray("goodPrice")),
        read.getItemStringArray("goodCurrency"),
        Utility.getIntArray(read.getItemStringArray("goodAmount")),
        read.getItemStringArray("goodUnit"));
        Collections.sort(goods, Comparator.comparingInt(good -> good.getINVENTORY_NUMBER()));
        windowController.addRowTGoodsInventoryOverview(goods);
    }

    private void loadFuelDeliveries() {
        File[] files = new File(DATA_SUB_PATHS[2]).listFiles();
        for (File file : files) {
            importFuelDelivery(file.getAbsolutePath(), false);
        }
    }

    private void loadGoodDeliveries() {
        File[] files = new File(DATA_SUB_PATHS[4]).listFiles();
        for (File file : files) {
            importGoodDelivery(file.getAbsolutePath(), false);
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
     * @author Robin Herder
     */
    private void createGood(int[] invNumber, float[] price, String[] currency, int[] amount, String[] unit) {
        ArrayList<ItemType> goodTypes = Utility.getInventoryType(types, InventoryType.Good);
        for (int i = 0; i < invNumber.length; i++) {
            ItemType good = null;
            for (ItemType goodType : goodTypes) {
                if (invNumber[i] == goodType.getINVENTORY_NUMBER()) {
                    good = goodType;
                    break;
                }
            }
            goods.add(new Good(good, price[i], currency[i], amount[i], unit[i]));
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

    public int getFreeEmployeeNumber() {
        int number = 0;
        Collections.sort(employees, Comparator.comparingInt(employee -> employee.getEMPLOYEE_NUMBER()));
        for(Employee employee: employees) {
            if(number != employee.getEMPLOYEE_NUMBER()) {
                break;
            }
            number++;
        }
        return number;
    }

    //===[ADD NEW OBJECT]==================================================

    public void addReceipt(ArrayList<Item> items) {
        ArrayList<Good> goods = new ArrayList<>();
        ArrayList<Fuel> fuels = new ArrayList<>();
        for(Item item : items) {
            if(item instanceof Good) {
                Good good = (Good) item;
                this.goods.get(this.goods.indexOf(good)).addAmount((int) good.getCheckoutAmount());
                goods.add(good);
            } else if(item instanceof Fuel)  {
                Fuel fuel = (Fuel) item;
                try {
                    this.fuels.get(this.fuels.indexOf(fuel)).addAmount(fuel.getCheckoutAmount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fuels.add(fuel);
            }
        }
        receipts.add(new CustomerOrder("", 0, new Date(), employees.get(0), fuels, goods));
    }

    /**
     * Neues Item hinzufügen
     * @param label
     * @param type
     * @author Robin Herder
     */
    public void addItemType(String label, InventoryType type) {
        ItemType newItemType = new ItemType(label, getFreeInvNumber(type), type);
        types.add(newItemType);
        Collections.sort(types, Comparator.comparingInt(itemType -> itemType.getINVENTORY_NUMBER()));
        windowController.addRowTFuelsSettingsFuel(types);
        windowController.addRowTGoodsSettingsGood(types);
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
            Collections.sort(tanks, Comparator.comparingInt(tank -> tank.getTANK_NUMBER()));
            windowController.addRowTTanksSettingsTank(tanks);
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
        Collections.sort(gasPumps, Comparator.comparingInt(gasPump -> gasPump.getGAS_PUMP_NUMBER()));
        windowController.addRowTGasPumpsSettingsGasPump(gasPumps);
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
            Collections.sort(fuels, Comparator.comparingInt(fuel -> fuel.getINVENTORY_NUMBER()));
            windowController.addRowTFuelsFuelOverview(fuels);
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
     * @param unit
     * @author Robin Herder
     */
    public void addGood(ItemType iType, int amount, float price, String currency, String unit) {
        boolean newEntry = true;
        for(Good good : goods) {
            if(good.getTYPE() == iType && good.getUNIT().equals(unit)) {
                newEntry = false;
            }
        }

        if(newEntry) {
            Good newGood = new Good(iType, price, currency, amount, unit);
            goods.add(newGood);
            Collections.sort(goods, Comparator.comparingInt(good -> good.getINVENTORY_NUMBER()));
            windowController.addRowTGoodsInventoryOverview(goods);
        } else {
            displayError("Produkt exsistiert bereits", new Exception("duplicate entry"), false);
        }
        saveInventory();
    }

    public void addEmployee(String firstName, String surName, Date employeeDate, String userRole, String userPass) {
        UserRole role = null;
        switch (userRole) {
            case "Administrator":
                role = UserRole.admin;
                break;
            case "Angestellter":
                role = UserRole.employee;
                break;
            default:
                role = UserRole.assistant;
        }
        employees.add(new Employee(getFreeEmployeeNumber(), firstName, surName, employeeDate, role, DigestUtils.sha256Hex(userPass)));
        Collections.sort(employees, Comparator.comparingInt(employee -> employee.getEMPLOYEE_NUMBER()));
        windowController.addRowTEmployeesEmployeeOverview(employees);
        saveEmployees();
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
        write.addItemArray("fuelType", getInvNumberFuel());
        write.addItemArray("fuelPrice", getPriceFuel());
        write.addItemArray("fuelCurrency", getCurrencyFuel());
        write.addItemArray("fuelAmount", getAmountFuel());
        write.addItemArray("goodType", getInvNumberGood());
        write.addItemArray("goodPrice", getPriceGood());
        write.addItemArray("goodCurrency", getCurrencyGood());
        write.addItemArray("goodAmount", getAmountGood());
        write.addItemArray("goodUnit", getUnit());
        write.write(true);
    }

    private void saveEmployees() {
        WriteJSON write = new WriteJSON(DATA_FILE_PATH + DATA_FILE_NAMES[5]);
        write.addItemArray("userID", getEmployeeNumber());
        write.addItemArray("userPass", getEmployeePass());
        write.addItemArray("userRole", getEmployeeRoles());
        write.write(true);
        WriteFile writeFile = new WriteFile(DATA_FILE_PATH + DATA_FILE_NAMES[3]);
        writeFile.addLine("LASTUPDATE=" + Utility.getDateFormatted(new Date()));
        writeFile.addLine("EMPLOYEENR;FIRSTNAME;LASTNAME;EMPLOYMENTDATE");
        for(Employee employee : employees) {
            writeFile.addLine(employee.getEMPLOYEE_NUMBER() + ";" + employee.getFIRST_NAME() + ";" + employee.getSUR_NAME() + ";" + employee.getEMPLOYMENT_DATE_FORMATTED());
        }
        try {
            writeFile.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @return unit[]
     */
    private String[] getUnit() {
        String[] unit = new String[goods.size()];
        for(int i = 0; i < goods.size(); i++) {
            unit[i] = goods.get(i).getUNIT();
        }
        return unit;
    }

    private String[] getEmployeeNumber() {
        String[] employeeNumber = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeeNumber[i] = String.valueOf(employees.get(i).getEMPLOYEE_NUMBER());
        }
        return employeeNumber;
    }

    private String[] getEmployeePass() {
        String[] employeePass = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeePass[i] = employees.get(i).getPASS();
        }
        return employeePass;
    }

    private String[] getEmployeeRoles() {
        String[] employeeRole = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeeRole[i] = String.valueOf(employees.get(i).getIRole());
        }
        return employeeRole;
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

    public ArrayList<Good> getGoods() {
        return goods;
    }

    /**
     * Gibt alle Tanks zurpck
     * @return
     * @author Robin Herder
     */
    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    public String getEmployeeName() {
        return activeEmployee.getFIRST_NAME() + " " + activeEmployee.getSUR_NAME();
    }

    public String getEmployeeRole() {
        return activeEmployee.getRole();
    }

    public int getRoleID() {
        return activeEmployee.getIRole();
    }

    public ArrayList<String> getUserRoles() {
        ArrayList<String> userRoles = new ArrayList<>();
        userRoles.add(UserRole.admin.getRole());
        userRoles.add(UserRole.employee.getRole());
        userRoles.add(UserRole.assistant.getRole());
        return userRoles;
    }

    public ArrayList<GasPump> getUsedGasPumps() {
        ReadJSON read;
        ArrayList<GasPump> gasPumps = new ArrayList<>();
        try {
            read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[6]);
            String[] gasPumpNumber = read.getItemStringArray("gasPumpNumber");
            String[] fuelType = read.getItemStringArray("fuelType");
            String[] fuelAmount = read.getItemStringArray("fuelAmount");
            for(int i = 0; i < gasPumpNumber.length; i++) {
                for(GasPump gasPump : this.gasPumps) {
                    if(gasPump.getGAS_PUMP_NUMBER() == Integer.parseInt(gasPumpNumber[i])) {
                        gasPumps.add(gasPump);
                        gasPumps.get(gasPumps.size() - 1).setCheckoutAmount(Float.parseFloat(fuelAmount[i]));
                        for(Fuel fuel : fuels) {
                            if(fuel.getINVENTORY_NUMBER() == Integer.parseInt(fuelType[i])) {
                                gasPumps.get(gasPumps.size() - 1).setCheckoutFuel(fuel);
                            }
                        }
                    }
                }
            }
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        }
        return gasPumps;
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

    //===[GET ROWS FOR INPUT DIALOGS]==================================================

    public void importFuelDelivery(String path, boolean newDelivery) {
        ReadListFile read = new ReadListFile(path);
        String filename = FilenameUtils.removeExtension(new File(path).getName());
        String[][] lines = read.getLINES();
        ArrayList<String> label = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        ArrayList<DeliveredFuel> fuel = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (i % 2 == 0) {
                label.add(lines[i][0]);
                amount.add(Integer.parseInt(lines[i][1]));
            } else {
                price.add(Float.parseFloat(lines[i][1]));
            }
        }
        for (int i = 0; i < label.size(); i++) {
            int idxItemType = 0;
            for (int ii = 0; ii < types.size(); ii++) {
                if (types.get(ii).getLABEL().equals(label.get(i))) {
                    idxItemType = ii;
                }
            }
            fuel.add(new DeliveredFuel(types.get(idxItemType), price.get(i), "EUR", amount.get(i)));
        }
        documents.add(new FuelDocument(DocumentType.fuelDelivery, filename, read.getDate(), fuel));
        windowController.addRowTFuelsFuelDelivery((ArrayList<FuelDocument>) Utility.getDocument(documents, DocumentType.fuelDelivery));
        if(newDelivery) {
            addDeliveredFuels((ArrayList<DeliveredFuel>) ((FuelDocument) documents.get(documents.size() - 1)).getFuels());
        }
    }

    public void addDeliveredFuels(ArrayList<DeliveredFuel> deliveredFuels) {
        boolean[] exists = new boolean[deliveredFuels.size()];
        for(boolean entry : exists) {
            entry = false;
        }
        int max = fuels.size();
        for(int i = 0; i < max; i++) {
            for(DeliveredFuel fuel : deliveredFuels) {
                if(fuel.getINVENTORY_NUMBER() == fuels.get(i).getINVENTORY_NUMBER()) {
                    exists[deliveredFuels.indexOf(fuel)] = true;
                    try {
                        fuels.get(i).addAmount(fuel.getAmountDelivered());
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for(int i = 0; i < exists.length; i++) {
            if(!exists[i]) {
                displayError("Kraftstoff existiert nicht!", new Exception("Kraftstoff " + deliveredFuels.get(i) + " exsistiert nicht! Erstelle ihn im Einstellungs bereich!"), false);
            }
        }
        Collections.sort(fuels, Comparator.comparingInt(fuel -> fuel.getINVENTORY_NUMBER()));
        windowController.addRowTFuelsFuelOverview(fuels);
        saveInventory();
    }

    public void importGoodDelivery(String path, boolean newDelivery) {
        ReadTableFile read = new ReadTableFile(path);
        String filename = FilenameUtils.removeExtension(new File(path).getName());
        String lines[][] = read.getLINES();
        ArrayList<Integer> invNumber = new ArrayList<>();
        ArrayList<String> label = new ArrayList<>();
        ArrayList<String> unit = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        ArrayList<Good> good = new ArrayList<>();
        for(int i = 0; i < lines.length; i++) {
            invNumber.add(Integer.parseInt(lines[i][0]));
            label.add(lines[i][1]);
            unit.add(lines[i][2]);
            amount.add(Integer.parseInt(lines[i][3]));
            price.add(Float.parseFloat(lines[i][4]));
        }
        for(int i = 0; i < lines.length; i++) {
            int idxItemType = -1;
            for (int ii = 0; ii < types.size(); ii++) {
                if (types.get(ii).getINVENTORY_NUMBER() == invNumber.get(i)) {
                    idxItemType = ii;
                }
            }
            if(idxItemType == -1) {
                types.add(new ItemType(label.get(i), invNumber.get(i), InventoryType.Good));
                windowController.addRowTGoodsSettingsGood(types);
                idxItemType = types.size() - 1;
            }
            good.add(new Good(types.get(idxItemType), price.get(i), "EUR", amount.get(i), unit.get(i)));
        }
        documents.add(new GoodDocument(DocumentType.goodDelivery, filename, read.getDate(), good));
        windowController.addRowTGoodsInventoryDelivery((ArrayList<GoodDocument>) Utility.getDocument(documents, DocumentType.goodDelivery));
        if(newDelivery) {
            addDeliveredGoods(((GoodDocument) documents.get(documents.size() - 1)).getGoods());
        }

    }

    private void addDeliveredGoods(ArrayList<Good> deliveredGoods) {
        boolean[] added = new boolean[deliveredGoods.size()];
        for(boolean addedEntry : added) {
            addedEntry = false;
        }
        int max = goods.size();
        for(int i = 0; i < max; i++) {
            for(Good deliveredGood : deliveredGoods) {
                if(goods.get(i).getINVENTORY_NUMBER() == deliveredGood.getINVENTORY_NUMBER()) {
                    added[deliveredGoods.indexOf(deliveredGood)] = true;
                    goods.get(i).addAmount(deliveredGood.getAmount());
                }
            }
        }
        for(int i = 0; i < added.length; i++) {
            if(!added[i]) {
                for(ItemType type : Utility.getInventoryType(types, InventoryType.Good)){
                    if(type.getINVENTORY_NUMBER() == deliveredGoods.get(i).getINVENTORY_NUMBER()) {
                        goods.add(new Good(type, deliveredGoods.get(i).getPrice(), deliveredGoods.get(i).getCurrency(), deliveredGoods.get(i).getAmount(), deliveredGoods.get(i).getUNIT()));
                        break;
                    }
                }
            }
        }
        Collections.sort(goods, Comparator.comparingInt(good -> good.getINVENTORY_NUMBER()));
        windowController.addRowTGoodsInventoryOverview(goods);
        saveInventory();
    }

    /**
     * @param path
     * @param dir
     * @param theme
     * @throws IOException
     * @author Robin Herder
     */
    public String importFile(String path, int dir, String theme)
    throws IOException {
        String file;
        String extension;
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
        Path newPath = new File(DATA_SUB_PATHS[dir] + file + number + extension).toPath();
        Files.copy(new File(path).toPath(), newPath);
        return newPath.toString();
    }

    public boolean checkLogin(int id, String pass) {
        String passHash = DigestUtils.sha256Hex(pass);
        for(Employee employee : employees) {
            if(employee.logIn(id, passHash)) {
                activeEmployee = employee;
                return true;
            }
        }
        return false;
    }
}