package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.exceptions.NumberOutOfRangeException;
import gasStationSoftware.models.CustomerOrder;
import gasStationSoftware.models.DeliveredFuel;
import gasStationSoftware.models.Document;
import gasStationSoftware.models.DocumentType;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.Fuel;
import gasStationSoftware.models.FuelDocument;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.Good;
import gasStationSoftware.models.GoodDocument;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.Item;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.models.UserRole;
import gasStationSoftware.ui.ErrorDialog;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.ReadListFile;
import gasStationSoftware.util.ReadTableFile;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
import gasStationSoftware.util.WriteJSON;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Logic {

    private static Logic logic;
    private static WindowController windowController;

    private String title, theme;

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
            "tankSimulation.json", "allReceipts.json", "danielTheme.json"
    };

    private final String PROFILE_PICTURE = "/images/profile";

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
     * Konstruktor für Logic (Modell). Ruft methoden zur Ordenerüberprüfung auf.
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
     * @param windowController WindowController Instanz für Zugriffe aus der logic
     * @return logic
     * @author Robin Herder
     */
    public static Logic getInstance(WindowController windowController) {
        Logic.windowController = windowController;
        return getInstance();
    }

    //===[CHECK DIRS AND FILES]==================================================

    /**
     * Directory erstellen wenn sie nicht schon existieren
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
     * Erstellt mehrere Directories / aufruf checkDir
     * @param dirs Array mit Dateipfaden
     * @author Robin Herder
     */
    private void checkDirs(String[] dirs) {
        for (String dir : dirs) {
            checkDir(dir);
        }
    }

    /**
     * Überprüft ob Datendatein vorhanden sind. Falls nicht exportiert er diese oder erstellt diese.
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
                case "allReceipts.json":
                    exportJSONResources("allReceipts.json", "allReceipts.json");
                    break;
                case "danielTheme.json":
                    exportJSONResources("danielTheme.json", "themes\\daniel.json");
                    break;
                default:
                    throw new DataFileNotFoundException(file);
                }
            }
        }
    }

    //===[JSON EXTRACTION]==================================================

    /**
     * Kopiert json datei aus Resourcen ins Ziel
     * @param source Pfad der Quelldatei
     * @param file Pfad der Zieldatei
     * @author Robin Herder
     */
    private void exportJSONResources(String source, String file) {
        InputStream jsonSource = getClass().getResourceAsStream("/json/" + source);
        File jsonDestination = new File(DATA_FILE_PATH + file);
        try {
            FileUtils.copyInputStreamToFile(jsonSource, jsonDestination);
        } catch (Exception e) {
            displayError("Could not export data file from jar!", e, true);
        }
    }

    //===[DISPLAY ERRORS]==================================================

    /**
     * Zeigt Errors an nachdem ein try gescheitert ist
     * @param error Titel des Fehlers
     * @param e Geworfener fehler
     * @param end ob es ein Programm kritischer fehler ist true -> Programmende
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
     * Lädt alle Datein des Programms
     * @author Robin Herder
     */
    public void loadFiles() {
        try {
            loadSettings();
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
        try {
            loadReceipts();
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        }
        updateBalance();
    }

    /**
     * Lädt Settings datei und speißt diese ein. Führt zum setzen des Titels und setzen des Themes
     * @throws DataFileNotFoundException
     * @author Robin Herder
     */
    private void loadSettings()
    throws DataFileNotFoundException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[1]);
        theme = read.getItemString("theme");
        title = read.getItemString("title");
        String[] themes = new String[new File(DATA_SUB_PATHS[5]).listFiles().length];
        File[] files = new File(DATA_SUB_PATHS[5]).listFiles();
        for(int i = 0; i < themes.length; i++) {
            themes[i] = FilenameUtils.removeExtension(files[i].getName());
        }
        windowController.setComboboxThemes(themes, theme);
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
        windowController.setTitle(title);
    }

    /**
     * Lädt Angestellte save datei und json und speißt diese in Objekte ein
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
     * Lädt Inventar datei und erstellt daraus ItemTypes, Goods usw. Alle kerneelemente der Tankstelle
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

        createGasPumpObjects(read.getItemStringArrayListArray("gasPumpAssignedTanks", "gasPump"));
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

    /**
     * Lädt die Kraftstofflieferungen die bereits registriert sind (Importiert)
     * @author Robin Herder
     */
    private void loadFuelDeliveries() {
        File[] files = new File(DATA_SUB_PATHS[2]).listFiles();
        for (File file : files) {
            importFuelDelivery(file.getAbsolutePath(), false);
        }
    }

    /**
     * Lädt die Warenlieferungen die bereits registriert sind (Importiert)
     * @author Robin Herder
     */
    private void loadGoodDeliveries() {
        File[] files = new File(DATA_SUB_PATHS[4]).listFiles();
        for (File file : files) {
            importGoodDelivery(file.getAbsolutePath(), false);
        }
    }

    /**
     * Lädt Quittungen die bereits registriert sind
     * @throws DataFileNotFoundException
     * @author Robin Herder
     */
    private void loadReceipts()
    throws DataFileNotFoundException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[7]);
        String[] receiptNumber = read.getItemStringArray("receiptTitle");
        String[] receiptEmployee = read.getItemStringArray("receiptEmployees");
        String[] receiptDate = read.getItemStringArray("receiptDate");
        ArrayList<String>[] receiptGoods = read.getItemStringArrayListArray("receiptGoods", "goods");
        ArrayList<String>[] receiptGoodsAmount = read.getItemStringArrayListArray("receiptGoodsAmount", "amount");
        ArrayList<String>[] receiptFuels = read.getItemStringArrayListArray("receiptFuels", "fuels");
        ArrayList<String>[] receiptFuelsAmount = read.getItemStringArrayListArray("receiptFuelsAmount", "amount");
        for (int i = 0; i < receiptNumber.length; i++) {
            Employee employee = null;
            for (Employee employeeItem : employees) {
                if (employeeItem.getEMPLOYEE_NUMBER() == Integer.parseInt(receiptEmployee[i])) {
                    employee = employeeItem;
                    break;
                }
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("dd.MM.yyyy").parse(receiptDate[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ArrayList<Good> goods = new ArrayList<>();
            ArrayList<Fuel> fuels = new ArrayList<>();
            for (String receiptGood : receiptGoods[i]) {
                for (Good good : this.goods) {
                    if (good.getINVENTORY_NUMBER() == Integer.parseInt(receiptGood)) {
                        goods.add(good);
                        goods.get(goods.size() - 1).setCheckoutAmount(
                        Integer.parseInt(receiptGoodsAmount[i].get(receiptGoods[i].indexOf(receiptGood))));
                    }
                }
            }
            for (String receiptFuel : receiptFuels[i]) {
                for (Fuel fuel : this.fuels) {
                    if (fuel.getINVENTORY_NUMBER() == Integer.parseInt(receiptFuel)) {
                        fuels.add(fuel);
                        fuels.get(fuels.size() - 1).setCheckoutAmount(
                        Float.parseFloat(receiptFuelsAmount[i].get(receiptFuels[i].indexOf(receiptFuel))));
                    }
                }
            }
            receipts.add(new CustomerOrder(Integer.parseInt(receiptNumber[i]), date, employee, fuels, goods));
        }
    }

    //===[CREATE OBJECTS FROM JSON]==================================================

    /**
     * Erstellt Item-Objekte aus der Inventory.json
     * @param label Die Bezeichner der ItemTypes
     * @param inventoryNumber Die Inventarnummer der ItemTypes
     * @param type Die type bezeeichnung equ InventoryType
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
     * Erstellt Tankobjekte aus Inventory.json
     * @param tankID Die ID der Kraftstofftanks
     * @param tankCapacity Die Kapazität der Kraftstofftanks
     * @param tankLevel Der Füllstand der Kraftstofftanks
     * @param tankAssignedFuels Die ID der Kraftstoffs des zugewiesenen Tanks
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
     * Erstellt Zapfsäulenobjekte aus Inventory.json
     * @param gasPumps Die IDs der Angeschlossenen Tanks an die Zapfsäule
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
     * Erstellt Kraftstoffobjekte aus Inventory.json
     * @param invNumber Die ID der Krafftstoffe
     * @param price Der Preis der Krafftstoffe
     * @param currency Die Währung der Krafftstoffe
     * @param amount Die menge der Krafftstoffe
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
            fuels.add(new Fuel(fuel, price[i], currency[i]));
        }
    }

    /**
     * Erstellt Warenobjekte aus Inventory.json
     * @param invNumber Die ID der Produkte
     * @param price Der Preis der Produkte
     * @param currency Die Währung der Produkte
     * @param amount Die Menge der Produkte
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
     * Gibt die nächste freie Inventarnummer zurück des angegeben ItemTypes
     * @param type InventoryType zur unterscheidung zwischen fuel und good
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

    /**
     * Gibt die nächste freie Mitarbeiternummer zurück
     * @return freeEmployeeNumber
     * @author Robin Herder
     */
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

    /**
     * Neue Quittung hinzufügen
     * @param items Die Items für die Quittung Good und Fuel möglich aufgrund von vererbung
     * @author Robin Herder
     */
    public void addReceipt(ArrayList<Item> items) {
        ArrayList<Good> goods = new ArrayList<>();
        ArrayList<Fuel> fuels = new ArrayList<>();
        for(Item item : items) {
            if(item instanceof Good) {
                Good good = (Good) item;
                this.goods.get(this.goods.indexOf(good)).addAmount(- (int) good.getCheckoutAmount());
                goods.add(good);
            } else if(item instanceof Fuel)  {
                Fuel fuel = (Fuel) item;
                try {
                    this.fuels.get(this.fuels.indexOf(fuel)).removeAmount(fuel.getCheckoutAmount(), fuel.getCheckoutTank());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fuels.add(fuel);
            }
        }
        receipts.add(new CustomerOrder(receipts.size(), new Date(), activeEmployee, fuels, goods));
        saveReceipt();
        windowController.addRowTFuelsFuelOverview(this.fuels);
        windowController.addRowTGoodsInventoryOverview(this.goods);
        saveInventory();
    }

    /**
     * Neues ItemType hinzufügen
     * @param label Label des zu erstellenden ItemType
     * @param type InventoryType des ItemTypes
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
     * @param capacity Kapazität des zu erstellenden Tanks
     * @param level Füllstand des zu erstellenden Tanks
     * @param fuel Kraftstoffid des zu erstellenden Tanks
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
     * @param tanks Liste aus Tank Objekten die an der Zapfsäule angeschlossen sind
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
     * @param iType ItemType des zu erstellenden Kraftstoffs
     * @param amount Menge des zu erstellenden Kraftstoffs
     * @param price Preis des zu erstellenden Kraftstoffs
     * @param currency Währung des erstellenden Kraftstoffs
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
            Fuel newFuel = new Fuel(iType, price, currency);
            try {
                newFuel.addAmount(amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
     * @param iType ItemType des zu erstellenden Produkts
     * @param amount Menge des zu erstellenden Produkts
     * @param price Preis des zu erstellenden Produkts
     * @param currency currency Währung des erstellenden Produkts
     * @param unit Einheit des zu erstellenden Produkts
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

    /**
     * Neuer Mitarbeiter hinzufügen
     * @param firstName Vorname des zu erstellenden Mitarbeiters
     * @param surName Nachname des zu erstellenden Mitarbeiters
     * @param employeeDate Einstellungsdatum des zu erstellenden Mitarbeiters
     * @param userRole Rechterolle des zu erstellenden Mitarbeiters
     * @param userPass Nutzerpasswort des zu erstellenden Mitarbeiters wird gehashed
     * @author Robin Herder
     */
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
     * Speichert aktuelle werte in Settings.json
     * @author Robin Herder
     */
    private void saveSettings() {
        WriteJSON write = new WriteJSON(DATA_FILE_PATH + DATA_FILE_NAMES[1]);
        write.addItem("theme", theme);
        write.addItem("title", title);
        write.write(true);
    }

    /**
     * Speichert aktuelle werte in Inventory.json
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
        updateBalance();
    }

    /**
     * Speichert aktuelle werte in Employees.txt und Users.json
     * @author Robin Herder
     */
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
        updateBalance();
    }

    /**
     * Speichert aktuelle werte in receipts.json
     * @author Robin Herder
     */
    private void saveReceipt() {
        WriteJSON write = new WriteJSON(DATA_FILE_PATH + DATA_FILE_NAMES[7]);
        write.addItemArray("receiptTitle", getReceiptTitle());
        write.addItemArray("receiptEmployees", getReceiptEmployeeNumber());
        write.addItemArray("receiptDate", getReceiptDate());
        write.addItemArrayListArray("receiptGoodsAmount", "amount", getReceiptGoodAmount());
        write.addItemArrayListArray("receiptFuelsAmount", "amount", getReceiptFuelAmount());
        write.addItemArrayListArray("receiptGoods", "goods", getReceiptGoods());
        write.addItemArrayListArray("receiptFuels", "fuels", getReceiptFuels());
        write.write(true);
        updateBalance();
    }

    //===[GET STRINGS FOR JSON]==================================================

    /**
     * Gibt den Titel aller Quittung zurück für json
     * @return receipt
     * @author Robin Herder
     */
    private String[] getReceiptTitle() {
        String[] receipt = new String[receipts.size()];
        for (int i = 0; i < receipt.length; i++) {
            receipt[i] = String.valueOf(receipts.get(i).getRECEIPT_NUMBER());
        }
        return receipt;
    }

    /**
     * Gibt den Employeenummer einer aller zurück für json
     * @return receipt
     * @author Robin Herder
     */
    private String[] getReceiptEmployeeNumber() {
        String[] receipt = new String[receipts.size()];
        for (int i = 0; i < receipt.length; i++) {
            receipt[i] = String.valueOf(receipts.get(i).getEMPLOYEE().getEMPLOYEE_NUMBER());
        }
        return receipt;
    }

    /**
     * Gibt die Daten aller Quittung zurück für json
     * @return recipeDate[]
     * @author Robin Herder
     */
    private String[] getReceiptDate() {
        String[] receipt = new String[receipts.size()];
        for (int i = 0; i < receipt.length; i++) {
            receipt[i] = receipts.get(i).getDate();
        }
        return receipt;
    }

    /**
     * Gibt die mengen allerKraftstoffe aller Quittung zurück für json
     * @return receiptGoods[]
     * @author Robin Herder
     */
    private ArrayList<String>[] getReceiptFuelAmount() {
        ArrayList<String>[] receiptFuels = new ArrayList[receipts.size()];
        for (int i = 0; i < receipts.size(); i++) {
            receiptFuels[i] = new ArrayList<>();
            ArrayList<Float> amount = receipts.get(i).getFuelsAmount();
            for (int j = 0; j < amount.size(); j++) {
                receiptFuels[i].add(String.valueOf(amount.get(j)));
            }
        }
        return receiptFuels;
    }

    /**
     * Gibt die mengen aller Produkte aller Quittung zurück für json
     * @return receiptGoods[]
     * @author Robin Herder
     */
    private ArrayList<String>[] getReceiptGoodAmount() {
        ArrayList<String>[] receiptGoods = new ArrayList[receipts.size()];
        for (int i = 0; i < receipts.size(); i++) {
            receiptGoods[i] = new ArrayList<>();
            ArrayList<Integer> amount = receipts.get(i).getGoodsAmount();
            for (int j = 0; j < amount.size(); j++) {
                receiptGoods[i].add(String.valueOf(amount.get(j)));
            }
        }
        return receiptGoods;
    }

    /**
     * Gibt die Kraftstoffe aller Quittung zurück für json
     * @return receiptFuels[]
     * @author Robin Herder
     */
    private ArrayList<String>[] getReceiptFuels() {
        ArrayList<String>[] receiptFuels = new ArrayList[receipts.size()];
        for (int i = 0; i < receipts.size(); i++) {
            receiptFuels[i] = new ArrayList<>();
            ArrayList<Fuel> fuels = receipts.get(i).getFuels();
            for (int j = 0; j < fuels.size(); j++) {
                receiptFuels[i].add(String.valueOf(fuels.get(j).getINVENTORY_NUMBER()));
            }
        }
        return receiptFuels;
    }

    /**
     * Gibt die Produkte aller Quittung zurück für json
     * @return receiptGoods[]
     * @author Robin Herder
     */
    private ArrayList<String>[] getReceiptGoods() {
        ArrayList<String>[] receiptGoods = new ArrayList[receipts.size()];
        for (int i = 0; i < receipts.size(); i++) {
            receiptGoods[i] = new ArrayList<>();
            ArrayList<Good> goods = receipts.get(i).getGoods();
            for (int j = 0; j < goods.size(); j++) {
                receiptGoods[i].add(String.valueOf(goods.get(j).getINVENTORY_NUMBER()));
            }
        }
        return receiptGoods;
    }

    /**
     * Gibt Tankkapazität aller Tanks zurück für json
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
     * Gibt Füllstand aller Tanks zurück json
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
     * Gibt die Kraftstoffarten der Tanks zurück für json
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
     * Gibt die ID des Tanks zurück für json
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
     * Gibt die Bezeichnung aller Items zurück für json
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
     * Gibt die Inventarnummer aller Items zurück für json
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
     * Gibt den Itemtyp aller Items zurück für json
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
     * Gibt die Tanks einer Zapfsäule zurück für json
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
     * Gibt die Inventarnummer aller Kraftstoffe zurück für json
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
     * Gibt den Preis aller Kraftstoffe zurück für json
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
     * Gibt die Währung der Kraftstoff preise zurück für json
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
     * Gibt die Menge an Kraftstoff zurück für json
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
     * Gibt die Inventarnummer aller Waren zurück für json
     * @return invNum[]
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
     * Gibt den Preis aller Waren zurück für json
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
     * Gibt die Währung der Produkte preise zurück für json
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
     * Gibt die menge aller Waren zurück für json
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
     * Gibt die Einheit zurück für json
     * @return unit[]
     * @author Robin Herder
     */
    private String[] getUnit() {
        String[] unit = new String[goods.size()];
        for(int i = 0; i < goods.size(); i++) {
            unit[i] = goods.get(i).getUNIT();
        }
        return unit;
    }

    /**
     * Gibt die Mitarbeiternummern zurück für json
     * @return employeeNumber[]
     * @author Robin Herder
     */
    private String[] getEmployeeNumber() {
        String[] employeeNumber = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeeNumber[i] = String.valueOf(employees.get(i).getEMPLOYEE_NUMBER());
        }
        return employeeNumber;
    }

    /**
     * Gibt den Mitarbeiterpass zurück für json
     * @return employeePass[]
     * @author Robin Herder
     */
    private String[] getEmployeePass() {
        String[] employeePass = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeePass[i] = employees.get(i).getPASS();
        }
        return employeePass;
    }

    /**
     * Gibt die Mitarbeiterrollen zurück für json
     * @return employeeRole[]
     * @author Robin Herder
     */
    private String[] getEmployeeRoles() {
        String[] employeeRole = new String[employees.size()];
        for(int i = 0; i < employees.size(); i++) {
            employeeRole[i] = String.valueOf(employees.get(i).getIRole());
        }
        return employeeRole;
    }

    //===[UPDATE]==================================================

    /**
     * Updatet die Bilanz / fügt alle einkäufe und verkäufe der bilanz hinzu und berechnet einnahmen usw
     * @author Robin Herder
     */
    public void updateBalance() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        ArrayList<Document> documents = new ArrayList<>();
        if (windowController.noTimeSpan()) {
            for (Document document : this.documents) {
                if (document instanceof FuelDocument || document instanceof GoodDocument) {
                    documents.add(document);
                }
            }
            documents.addAll(receipts);
        } else {
            ArrayList<Date> dates = windowController.getReportDates();
            ArrayList<Document> tmpDocument = new ArrayList<>();
            tmpDocument.addAll(receipts);
            tmpDocument.addAll(this.documents);
            if (dates.get(0).compareTo(dates.get(1)) < 0) {
                for (Document document : tmpDocument) {
                    Date oDate = null;
                    try {
                        oDate = format.parse(Utility.getDateFormatted(document.getODATE()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dates.get(0).compareTo(oDate) <= 0 && dates.get(1).compareTo(oDate) >= 0) {
                        documents.add(document);
                    }
                }

            } else {
                for (Document document : tmpDocument) {
                    Date oDate = null;
                    try {
                        oDate = format.parse(Utility.getDateFormatted(document.getODATE()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dates.get(1).compareTo(oDate) <= 0 && dates.get(0).compareTo(oDate) >= 0) {
                        documents.add(document);
                    }
                }
            }
        }
        Collections.sort(documents, Comparator.comparing(Document::getODATE));
        windowController.updateBalance(documents, getDeliveryCosts(), getSales(), getBalance());
        float today = 0, month = 0, year = 0;
        if(activeEmployee != null) {
            for(CustomerOrder document : receipts) {
                if(Utility.getDateFormatted(new Date()).equals(Utility.getDateFormatted(document.getODATE())) && document.getEMPLOYEE().getEMPLOYEE_NUMBER() == activeEmployee.getEMPLOYEE_NUMBER()) {
                    today += document.getTotal();
                }
                if(new Date().getYear() == document.getODATE().getYear() && new Date().getMonth() == document.getODATE().getMonth() && document.getEMPLOYEE().getEMPLOYEE_NUMBER() == activeEmployee.getEMPLOYEE_NUMBER()){
                    month += document.getTotal();
                }
                if(new Date().getYear() == document.getODATE().getYear() && document.getEMPLOYEE().getEMPLOYEE_NUMBER() == activeEmployee.getEMPLOYEE_NUMBER()){
                    year += document.getTotal();
                }
            }
        }
        windowController.updateEmployeeBalance(today, month, year);
    }

    //===[GETTER]==================================================

    /**
     * Gibt den titel des theme zurück
     * @author Robin Herder
     */
    public String getThemeTitle() {
        return theme;
    }

    /**
     * Gibt die Lieferkosten zurück
     * @return cost
     * @author Robin Herder
     */
    private float getDeliveryCosts() {
        float cost = 0;
        for (Document document : documents) {
            if (document instanceof FuelDocument) {
                cost += ((FuelDocument) document).getTotal();
            } else if (document instanceof GoodDocument) {
                cost += ((GoodDocument) document).getTotal();
            }
        }
        return cost;
    }

    /**
     * Gibt den Umsatz zurück
     * @return sales
     * @author Robin Herder
     */
    private float getSales() {
        float sales = 0;
        for (CustomerOrder receipt : receipts) {
            sales += receipt.getTotal();
        }
        return Utility.round(sales, 2);
    }

    /**
     * Gibt den Gewinn zurück
     * @return balance
     * @author Robin Herder
     */
    private float getBalance() {
        return Utility.round((getSales() - getDeliveryCosts()), 2);
    }

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
     * Gibt alle Itemtypes zurück fuel oder good
     * @param type InventoryType um good und fuel zu unterscheiden
     * @return types[]
     * @author Robin Herder
     */
    public ArrayList<ItemType> getItemTypes(InventoryType type) {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        return types;
    }

    /**
     * Gibt alle Good Objekte zurück
     * @return goods[]
     * @author Robin Herder
     */
    public ArrayList<Good> getGoods() {
        return goods;
    }

    /**
     * Gibt alle Tanks zurpck
     * @return tanks[]
     * @author Robin Herder
     */
    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    /**
     * Gibt den Name des angemeldeten Mitarbeiter zurück
     * @return employeeName
     * @author Robin Herder
     */
    public String getEmployeeName() {
        return activeEmployee.getFIRST_NAME() + " " + activeEmployee.getSUR_NAME();
    }

    /**
     * Gibt die Rolle des angemeldeten Mitarbeiter zurück als Zeichenkette
     * @return employeeRole
     * @author Robin Herder
     */
    public String getEmployeeRole() {
        return activeEmployee.getRole();
    }

    /**
     * Gibt die Rolle des angemeldeten Mitarbeiters zurück
     * @return employeeRole
     * @author Robin Herder
     */
    public int getRoleID() {
        return activeEmployee.getIRole();
    }

    /**
     * Gibt nutzerrollen als Liste zurück
     * @return userRoles[]
     * @author Robin Herder
     */
    public ArrayList<String> getUserRoles() {
        ArrayList<String> userRoles = new ArrayList<>();
        userRoles.add(UserRole.admin.getRole());
        userRoles.add(UserRole.employee.getRole());
        userRoles.add(UserRole.assistant.getRole());
        return userRoles;
    }

    /**
     * Gibt die benutzten Zapfsäulen zurück
     * @return gasPumps[]
     * @author Robin Herder
     */
    public ArrayList<GasPump> getUsedGasPumps() {
        ReadJSON read;
        ArrayList<GasPump> gasPumps = new ArrayList<>();
        try {
            read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[6]);
            String[] gasPumpNumber = read.getItemStringArray("gasPumpID");
            String[] fuelType = read.getItemStringArray("fuelType");
            String[] fuelAmount = read.getItemStringArray("fuelAmount");
            for(int i = 0; i < gasPumpNumber.length; i++) {
                for(GasPump gasPump : this.gasPumps) {
                    if(gasPump.getGAS_PUMP_NUMBER() == Integer.parseInt(gasPumpNumber[i]) && !fuelType[i].equals("null")) {
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

    /**
     * Importier die Kraftstofflieferung
     * @param path pfad der datei
     * @param newDelivery Wahrheitswert ob es eine neue oder bereits abgerechnete lieferung ist
     * @author Robin Herder
     */
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

    /**
     * Fügt die gelieferten Kraftstoffe hinzu
     * @param deliveredFuels die geliferten Kraftstoffe
     * @author Robin Herder
     */
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

    /**
     * Importiert eine Warenlieferung
     * @param path pfad der datei
     * @param newDelivery Wahrheitswert ob es eine neue oder bereits abgerechnete lieferung ist
     * @author Robin Herder
     */
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

    /**
     * Fügt die gelieferten Warem hinzu
     * @param deliveredGoods liste der ggeliferten Produkte
     * @author Robin Herder
     */
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
     * Impotiert eine Datei
     * @param path pfad der zu kopierenden datei
     * @param dir int des verzeichnisses in das die Datei Kopiert werden sollen
     * @param theme falls es ein theme ist der titel des themes
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

    /**
     * Überprüft Mitarbeiternummer und Passwort und gibt zurück ob es ein valider login ist und setzt angeeldeten nutzer
     * @param id eingegebene Persnummer
     * @param pass eingegebenes nutzerpasswort
     * @return boolean
     * @author Robin Herder
     */
    public boolean checkLogin(int id, String pass) {
        loadProfilePicture();
        String passHash = DigestUtils.sha256Hex(pass);
        for(Employee employee : employees) {
            if(employee.logIn(id, passHash)) {
                activeEmployee = employee;
                updateBalance();
                return true;
            }
        }
        return false;
    }

    /**
     * Mitarbeiter bearbeiten
     * @param employeeNumber persnummer der zu edditierenden  mitarbeiters
     * @param firstName vorname der zu edditierenden  mitarbeiters
     * @param surName nachname der zu edditierenden  mitarbeiters
     * @param userRole nutzerrolle der zu edditierenden  mitarbeiters
     * @param pass passwort der zu edditierenden  mitarbeiters
     * @author Robin Herder
     */
    public void editEmployee(int employeeNumber, String firstName, String surName, String userRole, String pass) {
        Employee editEmployee = null;
        for(Employee employee : employees) {
            if(employee.getEMPLOYEE_NUMBER() == employeeNumber) {
                editEmployee = employee;
                break;
            }
        }
        editEmployee.setFirstName(firstName);
        editEmployee.setSurName(surName);
        if(pass != null && !pass.equals("")) {
            editEmployee.setPass(DigestUtils.sha256Hex(userRole));
        }
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
        editEmployee.setRole(role);
        windowController.addRowTEmployeesEmployeeOverview(employees);
        saveEmployees();
    }

    /**
     * ItemType bearbeiten
     * @param id id des zu bearbeitenden ItemTypes
     * @param label label des zu bearbeitenden ItemTypes
     * @param type InventoryType des zu bearbeitenden ItemTypes
     * @author Robin Herder
     */
    public void editItemType(int id, String label, InventoryType type) {
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, type);
        ItemType editEntry = null;
        for (ItemType typeEntry : types) {
            if (id == typeEntry.getINVENTORY_NUMBER()) {
                editEntry = typeEntry;
                break;
            }
        }
        editEntry.setLabel(label);
        if (type == InventoryType.Fuel) {
            windowController.addRowTFuelsSettingsFuel(Utility.getInventoryType(this.types, InventoryType.Fuel));
        } else {
            windowController.addRowTGoodsSettingsGood(Utility.getInventoryType(this.types, InventoryType.Good));
        }
        saveInventory();
    }

    /**
     * Kraftstofftank bearbeiten
     * @param id id des zu bearbeitenden Kraftstofftanks
     * @param capacity kapazität des zu bearbeitenden Kraftstofftanks
     * @param level level des zu bearbeitenden Kraftstofftanks
     * @param index id des krafftstoffs des zu bearbeitenden Kraftstofftanks
     * @author Robin Herder
     */
    public void editFuelTank(int id, float capacity, float level, int index) {
        FuelTank editFuelTank = null;
        for (FuelTank tank : tanks) {
            if (tank.getTANK_NUMBER() == id) {
                editFuelTank = tank;
                break;
            }
        }
        ArrayList<ItemType> types = Utility.getInventoryType(this.types, InventoryType.Fuel);
        editFuelTank.setFuel(Utility.getInventoryType(types, InventoryType.Fuel).get(index));
        editFuelTank.setCAPACITY(capacity);
        try {
            editFuelTank.setLevel(level);
        } catch (NumberOutOfRangeException e) {
            e.printStackTrace();
        }
        windowController.addRowTTanksSettingsTank(tanks);
        saveInventory();
    }

    /**
     * Zapfsäule bearbeiten
     * @param tanks angeschlossene tanks der zu bearbeitenden Zapfsäule
     * @param id id der zu berabeitenden Zapfsäule
     * @author Robin Herder
     */
    public void editGasPump(ArrayList<FuelTank> tanks, int id) {
        GasPump editGasPump = null;
        for(GasPump gasPump : gasPumps) {
            if(gasPump.getGAS_PUMP_NUMBER() == id) {
                editGasPump = gasPump;
                break;
            }
        }
        editGasPump.setTanks(tanks);
        windowController.addRowTGasPumpsSettingsGasPump(gasPumps);
        saveInventory();
    }

    /**
     * Titel des themes setzen
     * @param theme titel des themes
     * @exception DataFileNotFoundException
     * @author Robin Herder
     */
    public void setTheme(String theme) throws DataFileNotFoundException {
        this.theme = theme;
        saveSettings();
        loadSettings();
    }

    /**
     * Titel der Tankstelle setzen
     * @param title titel der Tankstelle
     * @author Robin Herder
     */
    public void setTitle(String title) {
        this.title = title;
        saveSettings();
    }

    /**
     * laden der Profilbilder auf grund eines Random (1 von 5 noicen Pics)
     * @author Robin Herder
     */
    private void loadProfilePicture() {
        Random rand = new Random();
        int pic = rand.nextInt(5);
        Image image = new Image(getClass().getResourceAsStream(PROFILE_PICTURE + pic + ".png"));
        windowController.setProfilePicture(image);
    }
}