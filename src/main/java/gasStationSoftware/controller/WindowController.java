package gasStationSoftware.controller;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.ui.ItemInputDialog;
import gasStationSoftware.ui.FuelTankInputDialog;
import gasStationSoftware.ui.GasPumpInputDialog;
import gasStationSoftware.ui.ItemTypeInputDialog;
import gasStationSoftware.util.Dialog;
import gasStationSoftware.util.Utility;
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
    @FXML private JFXButton btnOrderInventoryOverview, btnDeliveriesInventoryOverview, btnGroceriesInventoryOrder, btnOtherInventoryOrder, btnAdultInventoryOrder;
    @FXML private JFXButton btnCanelInventoryOrder, btnSubmitInventoryOrder, btnCancelInventoryDelivery, btnImportInventoryDelivery, btnAddFuelOverview;
    @FXML private MaterialDesignIconView icoOrderInventoryOverview, icoDeliveryInventoryOverview, icoAddFuelOverview;
    @FXML private TableView tGoodsInventoryOverview, tGoodsInventoryOrder, tGoodsInventoryDelivery;


    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane;
    @FXML private Polygon polygonFuel;
    @FXML private Label titleFuelOverview, titleFuelOrder, titleFuelDeliveries;
    @FXML private JFXButton btnDeliveriesFuelOverview, btnOrdersFuelOverview, btnSubmitFuelOrder, btnCancelFuelOrder, btnCancelFuelDeliveries, btnImportFuelDeliveries;
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

    @FXML private AnchorPane settingsPane, settingsOverviewPane, settingsFuelPane, settingsTankPane, settingsGasPumpPane, settingsGoodPane;
    @FXML private Polygon polygonSettings;
    @FXML private JFXButton btnEditThemeSettingsOverview, btnCreateThemeSettingsOverview, btnFuelsSettingsOverview, btnTanksSettingsOverview, btnGasPumpsSettingsOverview;
    @FXML private JFXButton btnGoodsSettingsOverview, btnExportSettingsOverview, btnImportSettingsOverview, btnNewSettingsFuel, btnEditSettingsFuel, btnNewSettingsTank;
    @FXML private JFXButton btnEditSettingsTank, btnNewSettingsGasPump, btnEditSettingsGasPump, btnNewSettingsGood, btnEditSettingsGood;
    @FXML private JFXComboBox cbThemeSettingsOverview, cbTypeSettingsOverview;
    @FXML private TableView tFuelsSettingsFuel, tTanksSettingsTank, tGasPumpsSettingsGasPump, tGoodsSettingsGood;

    @FXML private ArrayList<AnchorPane> panes, subPanes;

    //===[INIT]==================================================

    @Override public void initialize(URL location, ResourceBundle resources) {
        logic = Logic.getInstance(this);
        logic.loadFiles();
        addColumnsTEmployeesEmployeeOverview();
        addColumnsTFuelsSettingsFuel();
        addColumnsTTanksSettingsTank();
        addColumnsTGasPumpsSettingsGasPump();
        addColumnsTGoodsSettingsGood();
        setDefaultContent();
    }

    //===[HANDLE EVENT]==================================================

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

    @FXML private void handleUserAction(MouseEvent event) {}

    @FXML private void handleSaleAction(MouseEvent event) {}

    @FXML private void handleInventoryAction(MouseEvent event) {
        if(event.getTarget() == btnAddFuelOverview) {
            new ItemInputDialog(rootPane, this, InventoryType.Fuel);
        }
    }

    @FXML private void handleEmployeeAction(MouseEvent event) {}

    @FXML private void handleReportAction(MouseEvent event) {}

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
        }
    }

    //===[HIDE PANES]==================================================

    private void hidePanes() {
        for (AnchorPane pane : panes) {
            pane.setVisible(false);
        }
        hideSubPanes();
    }

    private void hideSubPanes() {
        for (AnchorPane pane : subPanes) {
            pane.setVisible(false);
        }
    }

    //===[ADD COLUMNS TO TABLES]==================================================

    private void addColumnsTEmployeesEmployeeOverview() {
        TableColumn columnEmployeeNumber = Dialog.getColumn("Angestelter #", "EMPLOYEE_NUMBER", 80, true);
        TableColumn columnEmploymentDate = Dialog.getColumn("Einstellungsdatum", "EMPLOYMENT_DATE_FORMATTED", 100, true);
        TableColumn columnFirstName = Dialog.getColumn("Vorname", "FIRST_NAME", 200, true);
        TableColumn columnSurname = Dialog.getColumn("Nachname", "SUR_NAME", 200, true);
        tEmployeesEmployeeOverview.getColumns().addAll(columnEmployeeNumber, columnEmploymentDate, columnFirstName, columnSurname);
    }

    private void addColumnsTFuelsSettingsFuel() {
        tFuelsSettingsFuel.getColumns().addAll(getColumnsItemType());
    }

    private void addColumnsTTanksSettingsTank() {
        TableColumn columnTankNumber = Dialog.getColumn("Tank #", "TANK_NUMBER", 80, true);
        TableColumn columnTankCapacity = Dialog.getColumn("Max Tank Kapazität in l", "CAPACITY", 200, true);
        TableColumn columnTankLevel = Dialog.getColumn("Kraftstoff in tank in l", "level", 80, true);
        TableColumn columnTankLevelPercentage = Dialog.getColumn("Kraftstoff in tank in %", "levelPercentage", 200, true);
        TableColumn columnTankFuel = Dialog.getColumn("Kraftstoff Type", "fuelLabel", 200, true);
        TableColumn columnInvNumber = Dialog.getColumn("Inventar #", "invNumber", 200, true);
        tTanksSettingsTank.getColumns().addAll(columnTankNumber, columnTankCapacity, columnTankLevel, columnTankLevelPercentage, columnTankFuel, columnInvNumber);
    }

    private void addColumnsTGasPumpsSettingsGasPump() {
        TableColumn columnGasPumpNumber = Dialog.getColumn("Zapfsäule #", "GAS_PUMP_NUMBER", 80, true);
        TableColumn columnGasPumpFuel = Dialog.getColumn("Verfügbare Kraftstoffe", "assignedFuels", 200, true);
        TableColumn columnGasPumpTank = Dialog.getColumn("Angeschlossene Tanks", "assignedTanks", 200, true);
        tGasPumpsSettingsGasPump.getColumns().addAll(columnGasPumpNumber, columnGasPumpFuel, columnGasPumpTank);
    }

    private void addColumnsTGoodsSettingsGood() {
        tGoodsSettingsGood.getColumns().addAll(getColumnsItemType());
    }

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

    public void addRowTEmployeesEmployeeOverview(Employee employee){
        tEmployeesEmployeeOverview.getItems().add(employee);
    }

    public void addRowTFuelsSettingsFuel(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Fuel.getTYPE())) {
            tFuelsSettingsFuel.getItems().add(type);
        }
    }

    public void addRowTTanksSettingsTank(FuelTank tank){
        tTanksSettingsTank.getItems().add(tank);
    }

    public void addRowTGasPumpsSettingsGasPump(GasPump gasPump) {
        tGasPumpsSettingsGasPump.getItems().add(gasPump);
    }

    public void addRowTGoodsSettingsGood(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Good.getTYPE())) {
            tGoodsSettingsGood.getItems().add(type);
        }
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
        buttonsStyle =  "-jfx-button-type: RAISED;" +
                        "-fx-background-color: " + Utility.Rgb2Hex(buttonsBackground) + ";" +
                        "-fx-text-fill: " + Utility.Rgb2Hex(buttonsFont) + ";";
        iconsStyle = "-fx-fill: " + Utility.Rgb2Hex(icons) + ";";
    }

    private void applyTheme() {

    }

    //===[PROCESS INPUT]==================================================

    public void processItemTypeInput(String label, InventoryType type) {
       logic.addItemType(label, type);
    }

    public void processFuelTankInput(float capacity, float level, int index) {
        logic.addFuelTank(capacity, level, index);
    }

    public void processGasTankInput(ArrayList<FuelTank> tanks) {
        logic.addGasPump(tanks);
    }

    //===[GETTER]==================================================

    public static String getButtonStyle() {
        return buttonsStyle;
    }

    public static String getIconStyle() {
        return iconsStyle;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

}