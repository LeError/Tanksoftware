package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.Fuel;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.Good;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.models.StorageUnit;
import gasStationSoftware.ui.FuelTankInputDialog;
import gasStationSoftware.ui.GasPumpInputDialog;
import gasStationSoftware.ui.ItemInputDialog;
import gasStationSoftware.ui.ItemTypeInputDialog;
import gasStationSoftware.ui.StorageUnitInputDialog;
import gasStationSoftware.util.Dialog;
import gasStationSoftware.util.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WindowController
implements Initializable {

    private Logic logic;

    private final static String[] CB_SETTINGS_TYPE_OPTIONS = { "Settings", "Theme", "Inventory" };
    private final static String CB_SETTINGS_TYPE_PROMT = "Type auswählen";

    private static Color backgroundMenuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonsBackground, buttonsFont, dividerContent;
    private static String backgroundMenuBarStyle, contentPaneBackgroundStyle, iconsStyle, dividerMenuBarStyle, fontContentStyle, buttonsStyle, dividerContentStyle;

    @FXML private StackPane rootPane;

    @FXML private AnchorPane menuBarPane;
    @FXML private Line lineMenuBar;
    @FXML private ImageView ivUserMenuBar;
    @FXML private MaterialDesignIconView icoSellingMenuBar, icoInventoryMenuBar, icoTanksMenuBar, icoEmployeesMenuBar, icoReportsMenuBar, icoSettingsMenuBar;

    @FXML private AnchorPane userPane;
    @FXML private Polygon polygonUser;
    @FXML private ImageView ivUserProfilePictureUser;
    @FXML private Label titleUser, lblUserNameUser, lblUserRoleUser, subtitleSalesUser, lblDailySalesUser, lblMontlySalesUser, lblYearlySalesUser;
    @FXML private Label lblSalesSumDailyUser, lblSalesSumMonthlyUser, lblSalesSumYearlyUser, lblSalesSumDayCurrencyUser, lblSalesSumMonthCurrencyUser, lblSalesSumYearCurrencyUser;
    @FXML private Separator dividerUser;

    @FXML private AnchorPane sellingPane, sellingOverviewPane;
    @FXML private Polygon polygonSales;
    @FXML private Label titleSalesOverview, lblOrderSumSalesOverview, lblTotalSalesOverview, lblTotalCurrencySalesOverview;
    @FXML private JFXButton btnCheckOutSalesOverview, btnGoodsSalesOverview, btnGasPumpsSalesOverview;
    @FXML private MaterialDesignIconView icoCheckOutSalesOverview, icoGoodsSalesOverview, icoGasPumpsSalesOverview;
    @FXML private TableView tFuel;

    @FXML private AnchorPane inventoryPane, inventoryOverviewPane, inventoryOrderPane, inventoryDeliveryPane;
    @FXML private Polygon polygonInventory;
    @FXML private Label titleInventoryOverview, titleInventoryOrder, titleInventoryDelivery;
    @FXML private JFXButton btnOrderInventoryOverview, btnDeliveriesInventoryOverview, btnGroceriesInventoryOrder, btnOtherInventoryOrder, btnAdultInventoryOrder, btnAddGoodOverview;
    @FXML private JFXButton btnCanelInventoryOrder, btnSubmitInventoryOrder, btnCancelInventoryDelivery, btnImportInventoryDelivery;
    @FXML private MaterialDesignIconView icoOrderInventoryOverview, icoDeliveryInventoryOverview, icoAddFuelOverview;
    @FXML private TableView tGoodsInventoryOverview, tGoodsInventoryOrder, tGoodsInventoryDelivery;

    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane;
    @FXML private Polygon polygonFuel;
    @FXML private Label titleFuelOverview, titleFuelOrder, titleFuelDeliveries;
    @FXML private JFXButton btnDeliveriesFuelOverview, btnOrdersFuelOverview, btnSubmitFuelOrder, btnCancelFuelOrder, btnCancelFuelDeliveries, btnImportFuelDeliveries, btnAddFuelOverview;
    @FXML private MaterialDesignIconView icoDeiveriesFuelOverview, icoOrdersFuelOverview;
    @FXML private TableView tFuelsFuelOverview, tFuelsFuelOrder, tDeiveriesFuelDeliveries;

    @FXML private AnchorPane employeePane, employeeOverviewPane, employeeCreatePane;
    @FXML private Polygon polygonEmployee;
    @FXML private Label titleEmployeeOverview;
    @FXML private JFXButton btnCreateEmployeeOverview, btnEditEmployeeOverview;
    @FXML private TableView tEmployeesEmployeeOverview;

    @FXML private AnchorPane reportPane, reportOverviewPane;
    @FXML private Polygon polygonReport;
    @FXML private Label titleReportOverview, lblBalanceReportOverview, lblTimespanReportOverview, lblSalesReportOverview, lblCostsReportOverview, lblResultReportOverview;
    @FXML private Line dividerReportOverview, dividerBalanceReportOverview;
    @FXML private JFXDatePicker dpTimespanReportOverview, dpTimespanReportOverview1;
    @FXML private TableView tReportReportOverview;

    @FXML private AnchorPane settingsPane, settingsOverviewPane, settingsFuelPane, settingsTankPane, settingsGasPumpPane, settingsGoodPane, settingsStorageUnitPane;
    @FXML private Polygon polygonSettings;
    @FXML private JFXButton btnEditThemeSettingsOverview, btnCreateThemeSettingsOverview, btnFuelsSettingsOverview, btnTanksSettingsOverview, btnGasPumpsSettingsOverview;
    @FXML private JFXButton btnGoodsSettingsOverview, btnExportSettingsOverview, btnImportSettingsOverview, btnNewSettingsFuel, btnEditSettingsFuel, btnNewSettingsTank;
    @FXML private JFXButton btnEditSettingsTank, btnNewSettingsGasPump, btnEditSettingsGasPump, btnNewSettingsGood, btnEditSettingsGood, btnStorageUnitSettingsOverview;
    @FXML private JFXButton btnNewSettingsStorageUnit, btnEditSettingsStorageUnit;
    @FXML private JFXComboBox cbThemeSettingsOverview, cbTypeSettingsOverview;
    @FXML private TableView tFuelsSettingsFuel, tTanksSettingsTank, tGasPumpsSettingsGasPump, tGoodsSettingsGood, tGoodsSettingsStorageUnit;

    @FXML private ArrayList<AnchorPane> panes, subPanes;

    //===[INIT]==================================================

    /**
     *
     * @param location
     * @param resources
     * @author Robin Herder
     */
    @Override public void initialize(URL location, ResourceBundle resources) {
        logic = Logic.getInstance(this);
        logic.loadFiles();
        addColumnsTEmployeesEmployeeOverview();
        addColumnsTFuelsSettingsFuel();
        addColumnsTTanksSettingsTank();
        addColumnsTGasPumpsSettingsGasPump();
        addColumnsTGoodsSettingsGood();
        addColumnsTGoodsSettingsStorageUnit();
        addColumnsTFuelsFuelOverview();
        addColumnsTGoodsInventoryOverview();
        setDefaultContent();
        addExitButton();
    }

    //===[HANDLE EVENT]==================================================

    /**
     * Wechsel zwischen den Menus der linken menubar
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleMenuButtonAction(MouseEvent event) {
        hidePanes();
        hideSubPanes();
        if (event.getTarget() == ivUserMenuBar) {
            userPane.setVisible(true);
        } else if (event.getTarget() == icoSellingMenuBar) {
            sellingPane.setVisible(true);
            sellingOverviewPane.setVisible(true);
        } else if (event.getTarget() == icoInventoryMenuBar) {
            inventoryPane.setVisible(true);
            inventoryOverviewPane.setVisible(true);
        } else if (event.getTarget() == icoTanksMenuBar) {
            fuelPane.setVisible(true);
            fuelOverviewPane.setVisible(true);
        } else if (event.getTarget() == icoEmployeesMenuBar) {
            employeePane.setVisible(true);
            employeeOverviewPane.setVisible(true);
        } else if (event.getTarget() == icoReportsMenuBar) {
            reportPane.setVisible(true);
            reportOverviewPane.setVisible(true);
        } else if (event.getTarget() == icoSettingsMenuBar) {
            settingsPane.setVisible(true);
            settingsOverviewPane.setVisible(true);
        }
    }

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleUserAction(MouseEvent event) {}

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleSaleAction(MouseEvent event) {}

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleInventoryAction(MouseEvent event) {
        if (event.getTarget() == btnAddGoodOverview) {
            new ItemInputDialog(rootPane, this, InventoryType.Good);
        } else if(event.getTarget() == btnDeliveriesInventoryOverview) {
            hideSubPanes();
            inventoryDeliveryPane.setVisible(true);
        } else if(event.getTarget() == btnOrderInventoryOverview) {
            hideSubPanes();
            inventoryOrderPane.setVisible(true);
        }
    }

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleFuelAction(MouseEvent event) {
        if (event.getTarget() == btnAddFuelOverview) {
            new ItemInputDialog(rootPane, this, InventoryType.Fuel);
        } else if (event.getTarget() == btnDeliveriesFuelOverview) {
            hideSubPanes();
            fuelDeliveryPane.setVisible(true);
        } else if (event.getTarget() == btnOrdersFuelOverview) {
            hideSubPanes();
            fuelOrderPane.setVisible(true);
        }
    }

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleEmployeeAction(MouseEvent event) {}

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleReportAction(MouseEvent event) {}

    /**
     *
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleSettingsAction(MouseEvent event) {
        if (event.getTarget() == btnEditThemeSettingsOverview) {

        } else if (event.getTarget() == btnCreateThemeSettingsOverview) {

        } else if (event.getTarget() == btnFuelsSettingsOverview) {
            hideSubPanes();
            settingsFuelPane.setVisible(true);
        } else if (event.getTarget() == btnTanksSettingsOverview) {
            hideSubPanes();
            settingsTankPane.setVisible(true);
        } else if (event.getTarget() == btnGasPumpsSettingsOverview) {
            hideSubPanes();
            settingsGasPumpPane.setVisible(true);
        } else if (event.getTarget() == btnGoodsSettingsOverview) {
            hideSubPanes();
            settingsGoodPane.setVisible(true);
        } else if(event.getTarget() == btnStorageUnitSettingsOverview) {
            hideSubPanes();
            settingsStorageUnitPane.setVisible(true);
        } else if (event.getTarget() == btnExportSettingsOverview) {
        } else if (event.getTarget() == btnImportSettingsOverview) {
        } else if (event.getTarget() == btnNewSettingsFuel) {
            new ItemTypeInputDialog(rootPane, this, InventoryType.Fuel);
        } else if (event.getTarget() == btnNewSettingsTank) {
            new FuelTankInputDialog(rootPane, this);
        } else if (event.getTarget() == btnNewSettingsGasPump) {
            new GasPumpInputDialog(rootPane, this);
        } else if (event.getTarget() == btnNewSettingsGood) {
            new ItemTypeInputDialog(rootPane, this, InventoryType.Good);
        } else if (event.getTarget() == btnNewSettingsStorageUnit) {
            new StorageUnitInputDialog(rootPane, this);
        }
    }

    //===[HIDE PANES]==================================================

    /**
     * Panes ausblenden
     * @author Robin Herder
     */
    private void hidePanes() {
        for (AnchorPane pane : panes) {
            pane.setVisible(false);
        }
        hideSubPanes();
    }

    /**
     * Sub-Pane ausblenden
     * @author Robin Herder
     */
    private void hideSubPanes() {
        for (AnchorPane pane : subPanes) {
            pane.setVisible(false);
        }
    }

    //===[ADD COLUMNS TO TABLES]==================================================

    /**
     * Spalten Mitarbeiterüersicht einfügen
     * @author Robin Herder
     */
    private void addColumnsTEmployeesEmployeeOverview() {
        TableColumn columnEmployeeNumber = Dialog.getColumn("Angestelter #", "EMPLOYEE_NUMBER", 80, true);
        TableColumn columnEmploymentDate = Dialog.getColumn("Einstellungsdatum", "EMPLOYMENT_DATE_FORMATTED", 100, true);
        TableColumn columnFirstName = Dialog.getColumn("Vorname", "FIRST_NAME", 200, true);
        TableColumn columnSurname = Dialog.getColumn("Nachname", "SUR_NAME", 200, true);
        tEmployeesEmployeeOverview.getColumns().addAll(columnEmployeeNumber, columnEmploymentDate, columnFirstName, columnSurname);
    }

    /**
     * Spalten der Kraftstoffeinstellungen einfügen
     * @author Robin Herder
     */
    private void addColumnsTFuelsSettingsFuel() {
        tFuelsSettingsFuel.getColumns().addAll(getColumnsItemType());
    }

    /**
     * Spalten der Tankeinstellungen einfügen
     * @author Robin Herder
     */
    private void addColumnsTTanksSettingsTank() {
        TableColumn columnTankNumber = Dialog.getColumn("Tank #", "TANK_NUMBER", 80, true);
        TableColumn columnTankCapacity = Dialog.getColumn("Max Tank Kapazität in l", "CAPACITY", 200, true);
        TableColumn columnTankLevel = Dialog.getColumn("Kraftstoff in tank in l", "level", 80, true);
        TableColumn columnTankLevelPercentage = Dialog.getColumn("Kraftstoff in tank in %", "levelPercentage", 200, true);
        TableColumn columnTankFuel = Dialog.getColumn("Kraftstoff Type", "fuelLabel", 200, true);
        TableColumn columnInvNumber = Dialog.getColumn("Inventar #", "invNumber", 200, true);
        tTanksSettingsTank.getColumns().addAll(columnTankNumber, columnTankCapacity, columnTankLevel, columnTankLevelPercentage, columnTankFuel, columnInvNumber);
    }

    /**
     * Spalten der Zapfsäuleneinstellungen einfügen
     * @author Robin Herder
     */
    private void addColumnsTGasPumpsSettingsGasPump() {
        TableColumn columnGasPumpNumber = Dialog.getColumn("Zapfsäule #", "GAS_PUMP_NUMBER", 80, true);
        TableColumn columnGasPumpFuel = Dialog.getColumn("Verfügbare Kraftstoffe", "assignedFuels", 200, true);
        TableColumn columnGasPumpTank = Dialog.getColumn("Angeschlossene Tanks", "assignedTanks", 200, true);
        tGasPumpsSettingsGasPump.getColumns().addAll(columnGasPumpNumber, columnGasPumpFuel, columnGasPumpTank);
    }

    /**
     * Spalten der Wareneinstellungen einfügen
     * @author Robin Herder
     */
    private void addColumnsTGoodsSettingsGood() {
        tGoodsSettingsGood.getColumns().addAll(getColumnsItemType());
    }

    /**
     * Spalten der
     * @author Robin Herder
     */
    private void addColumnsTGoodsSettingsStorageUnit() {
        TableColumn columnStorageUnitLabel = Dialog.getColumn("Bezeichner", "label", 200, true);
        TableColumn columnStorageUnitX = Dialog.getColumn("Platzierung X", "x", 200, true);
        TableColumn columnStorageUnitY = Dialog.getColumn("Platzierung Y", "y", 200, true);
        tGoodsSettingsStorageUnit.getColumns().addAll(columnStorageUnitLabel, columnStorageUnitX, columnStorageUnitY);
    }

    /**
     *
     * @author Robin Herder
     */
    private void addColumnsTFuelsFuelOverview() {
        TableColumn columnFuelInvNumber = Dialog.getColumn("INV #", "INVENTORY_NUMBER", 80, true);
        TableColumn columnFuelLabel = Dialog.getColumn("Kraftstoff", "LABEL", 100, true);
        TableColumn columnFuelAmount = Dialog.getColumn("Menge in l", "amount", 100, true);
        TableColumn columnFuelPrice = Dialog.getColumn("Preis", "price", 100, true);
        TableColumn columnFuelCurrency = Dialog.getColumn("Währung", "currency", 100, true);
        TableColumn columnFuelTanks = Dialog.getColumn("In Tanks", "tanks", 100, true);
        tFuelsFuelOverview.getColumns().addAll(columnFuelInvNumber, columnFuelLabel, columnFuelAmount, columnFuelPrice, columnFuelCurrency, columnFuelTanks);
    }

    /**
     *
     * @author Robin Herder
     */
    private void addColumnsTGoodsInventoryOverview() {
        TableColumn columnGoodInvNumber = Dialog.getColumn("INV #", "INVENTORY_NUMBER", 80, true);
        TableColumn columnGoodLabel = Dialog.getColumn("Produkt", "LABEL", 100, true);
        TableColumn columnGoodAmount = Dialog.getColumn("Menge", "amount", 100, true);
        TableColumn columnGoodUnit = Dialog.getColumn("Einheit", "UNIT", 100, true);
        TableColumn columnGoodPrice = Dialog.getColumn("Preis", "price", 100, true);
        TableColumn columnGoodCurrency = Dialog.getColumn("Währung", "currency", 100, true);
        TableColumn columnGoodStorageUnit = Dialog.getColumn("Lagereinheit", "storageUnit", 100, true);
        tGoodsInventoryOverview.getColumns().addAll(columnGoodInvNumber, columnGoodLabel, columnGoodAmount, columnGoodUnit, columnGoodPrice, columnGoodCurrency, columnGoodStorageUnit);
    }

    /**
     *
     * @return  colums[]
     * @author Robin Herder
     */
    private TableColumn[] getColumnsItemType() {
        TableColumn columnInventoryNumber = new TableColumn("Inventar #");
        columnInventoryNumber.setCellValueFactory(new PropertyValueFactory<>("INVENTORY_NUMBER"));
        TableColumn columnType = new TableColumn("Typ");
        columnType.setCellValueFactory(new PropertyValueFactory<>("TYPE_LABEL"));
        TableColumn columnLabel = new TableColumn("Bezeichnung");
        columnLabel.setCellValueFactory(new PropertyValueFactory<>("LABEL"));
        TableColumn[] columns = {
                columnInventoryNumber,
                columnType,
                columnLabel
        };
        return columns;
    }

    //===[ADD ROWS TO TABLES]==================================================

    /**
     * Neuer Mitarbeiter der Mitarbeiterübersicht-Tabelle hinzufügen
     * @param employee
     * @author Robin Herder
     */
    public void addRowTEmployeesEmployeeOverview(Employee employee){
        tEmployeesEmployeeOverview.getItems().add(employee);
    }

    /**
     * Neuer Kraftstoff der Kraftstoffeinstellungen-Tabelle hinzufügen
     * @param type
     * @author Robin Herder
     */
    public void addRowTFuelsSettingsFuel(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Fuel.getTYPE())) {
            tFuelsSettingsFuel.getItems().add(type);
        }
    }

    /**
     * Neuer Tank der Tankeinstellungen-Tabelle hinzufügen
     * @param tank
     * @author Robin Herder
     */
    public void addRowTTanksSettingsTank(FuelTank tank){
        tTanksSettingsTank.getItems().add(tank);
    }

    /**
     * Neue Zapfsäuler der Zapfsäuleneinstellung-Tabelle hinzufügen
     * @param gasPump
     * @author Robin Herder
     */
    public void addRowTGasPumpsSettingsGasPump(GasPump gasPump) {
        tGasPumpsSettingsGasPump.getItems().add(gasPump);
    }

    /**
     * Neue Ware der Wareneinstellungen-Tabelle hinzufügen
     * @param type
     * @author Robin Herder
     */
    public void addRowTGoodsSettingsGood(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Good.getTYPE())) {
            tGoodsSettingsGood.getItems().add(type);
        }
    }

    /**
     *
     * @param storageUnit
     * @author Robin Herder
     */
    public void addRowTSettingsStorageUnit(StorageUnit storageUnit) {
        tGoodsSettingsStorageUnit.getItems().add(storageUnit);
    }

    /**
     * Kraftstoff der Kraftstoffübersichtstabelle hinzufügen
     * @param fuel
     * @author Robin Herder
     */
    public void addRowTFuelsFuelOverview(Fuel fuel) {
        tFuelsFuelOverview.getItems().add(fuel);
    }

    /**
     * Ware der Warenübersichtstabelle hinzufügen
     * @param good
     * @author Robin Herder
     */
    public void addRowTGoodsInventoryOverview(Good good) {
        tGoodsInventoryOverview.getItems().add(good);
    }

    /**
     * Exit-Buttons hinzufügen
     * @author Robin Herder
     */
    private void addExitButton() {
        MaterialDesignIconView icoExit = new MaterialDesignIconView(MaterialDesignIcon.CLOSE);
        icoExit.setGlyphSize(30);

        JFXButton exit = new JFXButton();
        exit.setGraphic(icoExit);
        exit.setPrefSize(40, 40);
        exit.setOnAction(event -> System.exit(0));
        AnchorPane.setRightAnchor(exit, 20d);
        AnchorPane.setTopAnchor(exit, 20d);
        for(AnchorPane pane : subPanes) {
            pane.getChildren().add(exit);
        }
    }

    //===[LOGIC CALL]==================================================

    public void addTankTableRows(TableView table) {
        logic.addTankTableRows(table);
    }

    //===[DEFAULT CONTENT]==================================================

    private void setDefaultContent() {
        cbTypeSettingsOverview.getItems().setAll((Object[]) CB_SETTINGS_TYPE_OPTIONS);
        cbTypeSettingsOverview.setPromptText(CB_SETTINGS_TYPE_PROMT);
    }

    public void setComboboxThemes(String[] themes, String selected) {
        cbThemeSettingsOverview.getItems().setAll((Object[]) themes);
        cbThemeSettingsOverview.getSelectionModel().select(selected);
    }

    //===[THEME]==================================================

    public void setTheme(Color backgroundMenuBar, Color contentPaneBackground, Color icons, Color dividerMenuBar,
    Color fontContent, Color buttonsBackground, Color buttonsFont, Color dividerContent) {
        this.backgroundMenuBar = backgroundMenuBar;
        this.contentPaneBackground = contentPaneBackground;
        this.icons = icons;
        this.dividerMenuBar = dividerMenuBar;
        this.fontContent = fontContent;
        this.buttonsBackground = buttonsBackground;
        this.buttonsFont = buttonsFont;
        this.dividerContent = dividerContent;
        buttonsStyle =  "-jfx-button-type: RAISED;" + "-jfx-disable-visual-focus: true;" +
                        "-fx-background-color: " + Utility.Rgb2Hex(buttonsBackground) + ";" +
                        "-fx-text-fill: " + Utility.Rgb2Hex(buttonsFont) + ";";
        iconsStyle = "-fx-fill: " + Utility.Rgb2Hex(icons) + ";";
    }

    private void applyTheme() {

    }

    //===[PROCESS INPUT]==================================================

    public void processItemTypeInput(AnchorPane pane, InventoryType type) {
        String label = ((JFXTextField) pane.getChildren().get(1)).getText();
        logic.addItemType(label, type);
    }

    public void processFuelTankInput(AnchorPane pane) {
        float capacity = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        int index = ((JFXComboBox<String>) pane.getChildren().get(3)).getSelectionModel().getSelectedIndex();
        logic.addFuelTank(capacity, level, index);
    }

    public void processGasPumpInput(AnchorPane pane) {
        TableView table = (TableView) pane.getChildren().get(1);
        ArrayList<FuelTank> tanks = new ArrayList<>();
        for(int i = 0; i < table.getItems().size(); i++) {
            tanks.add((FuelTank) table.getItems().get(i));
        }
        logic.addGasPump(tanks);
    }

    public void processStorageUnit(AnchorPane pane) {
        String label = ((JFXTextField) pane.getChildren().get(0)).getText();
        int x = Integer.parseInt(((JFXTextField) pane.getChildren().get(1)).getText());
        int y = Integer.parseInt(((JFXTextField) pane.getChildren().get(2)).getText());
        logic.addStorageUnit(label, x, y);
    }

    public void processFuel(AnchorPane pane, ItemType iType){
        float amount = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(3)).getText();
        logic.addFuel(iType, amount, price, currency);
    }

    public void processGood(AnchorPane pane, ItemType iType) {
        int amount = Integer.parseInt(((JFXTextField) pane.getChildren().get(1)).getText());
        String unit = ((JFXTextField) pane.getChildren().get(2)).getText();
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(3)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(4)).getText();
        String StorageUnit = (String) ((JFXComboBox) pane.getChildren().get(5)).getSelectionModel().getSelectedItem();
        logic.addGood(iType, amount, price, currency, StorageUnit, unit);
    }

    //===[CREATE SEARCHABLE DATA]==================================================

    public void createItemTypeData(ItemInputDialog itemInputDialog, InventoryType type) {
        ObservableList<ItemType> observableItemTypeList = FXCollections.observableArrayList();
        observableItemTypeList.addAll(logic.getItemTypes(type));
        FilteredList<ItemType> filteredItemType = new FilteredList<>(observableItemTypeList, o -> true);

        itemInputDialog.getTxtSearch().textProperty().addListener((observable, oldSearchValue, searchValue) ->
            filteredItemType.setPredicate(ItemType -> {
                if (searchValue == null || searchValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = searchValue.toLowerCase();
                if (ItemType.getLABEL().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(ItemType.getINVENTORY_NUMBER()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            }));

        SortedList<ItemType> sortedItemType = new SortedList<>(filteredItemType);
        sortedItemType.comparatorProperty().bind(itemInputDialog.getTable().comparatorProperty());

        itemInputDialog.getTable().setItems(sortedItemType);
    }

    //===[GETTER]==================================================

    public static String getButtonStyle() {
        return buttonsStyle;
    }

    public static String getIconStyle() {
        return iconsStyle;
    }

    public int getFreeInvNumber(InventoryType type) {
        return logic.getFreeInvNumber(type);
    }

    public int getTankNumber() {
        return logic.getFreeTankNumber();
    }

    public ArrayList<String> getFuel() {
        return logic.getFuel();
    }

    public StackPane getRootPane() {
        return rootPane;
    }

    public ArrayList<String> getStorageUnit() {
        return logic.getStorageUnit();
    }

    private String getFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showOpenDialog(rootPane.getScene().getWindow()).getAbsolutePath();
    }

}