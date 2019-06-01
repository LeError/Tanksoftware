package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.models.*;
import gasStationSoftware.ui.*;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WindowController
implements Initializable {

    private Logic logic;

    private final static String[] CB_SETTINGS_TYPE_OPTIONS = { "Settings", "Theme", "Inventory" };
    private final static String CB_SETTINGS_TYPE_PROMT = "Type auswählen";

    private static Color backgroundMenuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonsBackground, buttonsFont, dividerContent;
    private static String backgroundPolygon, backgroundMenuBarStyle, contentPaneBackgroundStyle, iconsStyle, dividerMenuBarStyle, fontStyle, buttonsStyle, dividerContentStyle;

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
    @FXML private JFXButton btnCheckOutSalesOverview, btnGoodsSalesOverview, btnGasPumpsSalesOverview, btnAddAmountSalesOverview, btnRemoveAmountSalesOverview, btnDeleteSalesOverview;
    @FXML private MaterialDesignIconView icoCheckOutSalesOverview, icoGoodsSalesOverview, icoGasPumpsSalesOverview;
    @FXML private TableView tCheckoutSellingOverview;

    @FXML private AnchorPane inventoryPane, inventoryOverviewPane, inventoryOrderPane, inventoryDeliveryPane;
    @FXML private Polygon polygonInventory;
    @FXML private Label titleInventoryOverview, titleInventoryOrder, titleInventoryDelivery;
    @FXML private JFXButton btnOrderInventoryOverview, btnDeliveriesInventoryOverview, btnGroceriesInventoryOrder, btnOtherInventoryOrder, btnAdultInventoryOrder, btnAddGoodOverview;
    @FXML private JFXButton btnCanelInventoryOrder, btnSubmitInventoryOrder, btnOpeInventoryDelivery, btnImportInventoryDelivery;
    @FXML private MaterialDesignIconView icoOrderInventoryOverview, icoDeliveryInventoryOverview, icoAddFuelOverview;
    @FXML private TableView tGoodsInventoryOverview, tGoodsInventoryOrder, tGoodsInventoryDelivery;

    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane;
    @FXML private Polygon polygonFuel;
    @FXML private Label titleFuelOverview, titleFuelOrder, titleFuelDeliveries;
    @FXML private JFXButton btnDeliveriesFuelOverview, btnOrdersFuelOverview, btnSubmitFuelOrder, btnCancelFuelOrder, btnOpenFuelDeliveries, btnImportFuelDeliveries, btnAddFuelOverview;
    @FXML private MaterialDesignIconView icoDeiveriesFuelOverview, icoOrdersFuelOverview;
    @FXML private TableView tFuelsFuelOverview, tFuelsFuelOrder, tFuelsFuelDeliveries;

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
    @FXML private ArrayList<MaterialDesignIconView> icoMenuBar, allIcons;
    @FXML private ArrayList<JFXButton> allButtons;
    @FXML private ArrayList<Polygon> allPolygons;
    @FXML private ArrayList<Separator> allDivider;
    @FXML private ArrayList<Label> allLabels, allTitles, allSubTitles;
    @FXML private ArrayList<TableView> allTableViews;
    @FXML private ArrayList<JFXComboBox> allCb;
    @FXML private ArrayList<ImageView> allIv;

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
        addColumnsTFuelsFuelOverview();
        addColumnsTGoodsInventoryOverview();
        addColumnsTFuelsFuelDelivery();
        addColumnsTGoodsInventoryDelivery();
        addColumnsTCheckoutSellingOverview();
        setDefaultContent();
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
    @FXML private void handleSaleAction(MouseEvent event) {
        if (event.getTarget() == btnGoodsSalesOverview) {
            new GoodsDialog(rootPane, this);
        } else if(event.getTarget() == btnGasPumpsSalesOverview) {

        } else if(event.getTarget() == btnCheckOutSalesOverview) {
            createReceipt();
        } else if (event.getTarget() == btnAddAmountSalesOverview) {
            incAmount();
        } else if (event.getTarget() == btnRemoveAmountSalesOverview) {
            decAmount();
        } else if (event.getTarget() == btnDeleteSalesOverview) {
            removeElement();
        }
    }

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
        } else if (event.getTarget() == btnImportInventoryDelivery) {
            try {
                logic.importGoodDelivery(logic.importFile(getFile("Lieferung importieren"), 4, null), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        } else if(event.getTarget() == btnImportFuelDeliveries) {
            try {
                logic.importFuelDelivery(logic.importFile(getFile("Kraftstofflieferung importieren"), 2, null), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        tGoodsInventoryOverview.getColumns().addAll(columnGoodInvNumber, columnGoodLabel, columnGoodAmount, columnGoodUnit, columnGoodPrice, columnGoodCurrency);
    }

    private void addColumnsTFuelsFuelDelivery() {
        TableColumn columnFuelDeliveryName = Dialog.getColumn("Lieferung", "NAME", 200, true);
        TableColumn columnFuelDeliveryDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tFuelsFuelDeliveries.getColumns().addAll(columnFuelDeliveryName, columnFuelDeliveryDate);
    }

    private void addColumnsTGoodsInventoryDelivery() {
        TableColumn columnGoodDeliveryName = Dialog.getColumn("Lieferung", "NAME", 200, true);
        TableColumn columnGoodDeliveryDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tGoodsInventoryDelivery.getColumns().addAll(columnGoodDeliveryName, columnGoodDeliveryDate);
    }

    private void addColumnsTCheckoutSellingOverview() {
        TableColumn columnCheckoutInvNumber = Dialog.getColumn("INV #","INVENTORY_NUMBER", 100, true);
        TableColumn columnCheckoutLabel = Dialog.getColumn("Bezeichnung","LABEL", 100, true);
        TableColumn columnCheckoutType = Dialog.getColumn("Type","INVENTORY_TYPE", 100, true);
        TableColumn columnCheckoutPrice = Dialog.getColumn("Preis","price", 100, true);
        TableColumn columnCheckoutAmount = Dialog.getColumn("Anz","checkoutAmount", 100, true);
        tCheckoutSellingOverview.getColumns().addAll(columnCheckoutInvNumber, columnCheckoutLabel, columnCheckoutType, columnCheckoutPrice, columnCheckoutAmount);
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
    public void addRowTEmployeesEmployeeOverview(ArrayList<Employee> employee){
        tEmployeesEmployeeOverview.getItems().clear();
        tEmployeesEmployeeOverview.getItems().addAll(employee);
    }

    /**
     * Neuer Kraftstoff der Kraftstoffeinstellungen-Tabelle hinzufügen
     * @param types
     * @author Robin Herder
     */
    public void addRowTFuelsSettingsFuel(ArrayList<ItemType> types) {
        types = Utility.getInventoryType(types, InventoryType.Fuel);
        tFuelsSettingsFuel.getItems().clear();
        tFuelsSettingsFuel.getItems().addAll(types);
    }

    /**
     * Neuer Tank der Tankeinstellungen-Tabelle hinzufügen
     * @param tanks
     * @author Robin Herder
     */
    public void addRowTTanksSettingsTank(ArrayList<FuelTank> tanks){
        tTanksSettingsTank.getItems().clear();
        tTanksSettingsTank.getItems().addAll(tanks);
    }

    /**
     * Neue Zapfsäuler der Zapfsäuleneinstellung-Tabelle hinzufügen
     * @param gasPumps
     * @author Robin Herder
     */
    public void addRowTGasPumpsSettingsGasPump(ArrayList<GasPump> gasPumps) {
        tGasPumpsSettingsGasPump.getItems().clear();
        tGasPumpsSettingsGasPump.getItems().addAll(gasPumps);
    }

    /**
     * Neue Ware der Wareneinstellungen-Tabelle hinzufügen
     * @param types
     * @author Robin Herder
     */
    public void addRowTGoodsSettingsGood(ArrayList<ItemType> types) {
        types = Utility.getInventoryType(types, InventoryType.Good);
        tGoodsSettingsGood.getItems().clear();
        tGoodsSettingsGood.getItems().addAll(types);
    }

    /**
     * Kraftstoff der Kraftstoffübersichtstabelle hinzufügen
     * @param fuels
     * @author Robin Herder
     */
    public void addRowTFuelsFuelOverview(ArrayList<Fuel> fuels) {
        tFuelsFuelOverview.getItems().clear();
        tFuelsFuelOverview.getItems().addAll(fuels);
    }

    /**
     * Ware der Warenübersichtstabelle hinzufügen
     * @param goods
     * @author Robin Herder
     */
    public void addRowTGoodsInventoryOverview(ArrayList<Good> goods) {
        tGoodsInventoryOverview.getItems().clear();
        tGoodsInventoryOverview.getItems().addAll(goods);
    }

    public void addRowTFuelsFuelDelivery(ArrayList<FuelDocument> deliveries) {
        tFuelsFuelDeliveries.getItems().clear();
        tFuelsFuelDeliveries.getItems().addAll(deliveries);
    }

    public void addRowTGoodsInventoryDelivery(ArrayList<GoodDocument> deliveries) {
        tGoodsInventoryDelivery.getItems().clear();
        tGoodsInventoryDelivery.getItems().addAll(deliveries);
    }

    //===[LOGIC CALL]==================================================

    /**
     *
     * @param table
     * @author Robin Herder
     */
    public void addTankTableRows(TableView table) {
        logic.addTankTableRows(table);
    }

    //===[DEFAULT CONTENT]==================================================

    /**
     *
     * @author Robin Herder
     */
    private void setDefaultContent() {
        cbTypeSettingsOverview.getItems().setAll((Object[]) CB_SETTINGS_TYPE_OPTIONS);
        cbTypeSettingsOverview.setPromptText(CB_SETTINGS_TYPE_PROMT);
    }

    /**
     *
     * @param themes
     * @param selected
     * @author Robin Herder
     */
    public void setComboboxThemes(String[] themes, String selected) {
        cbThemeSettingsOverview.getItems().setAll((Object[]) themes);
        cbThemeSettingsOverview.getSelectionModel().select(selected);
    }

    //===[THEME]==================================================

    /**
     * Theme einstellen
     * @param backgroundMenuBar
     * @param contentPaneBackground
     * @param icons
     * @param dividerMenuBar
     * @param fontContent
     * @param buttonsBackground
     * @param buttonsFont
     * @param dividerContent
     * @author Robin Herder
     */
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
        backgroundMenuBarStyle = "-fx-background-color: " + Utility.Rgb2Hex(backgroundMenuBar) + ";";
        backgroundPolygon =  "-fx-fill: " + Utility.Rgb2Hex(backgroundMenuBar) + ";";
        contentPaneBackgroundStyle = "-fx-background-color: " + Utility.Rgb2Hex(contentPaneBackground) + ";";
        buttonsStyle =  "-jfx-button-type: RAISED;" + "-jfx-disable-visual-focus: true;" +
                        "-fx-background-color: " + Utility.Rgb2Hex(buttonsBackground) + ";" +
                        "-fx-text-fill: " + Utility.Rgb2Hex(buttonsFont) + ";";
        dividerMenuBarStyle = "-fx-background-color: " + Utility.Rgb2Hex(dividerMenuBar) + ";";
        dividerContentStyle = "-fx-background-color: " + Utility.Rgb2Hex(dividerContent) + ";";
        fontStyle = "-fx-text-fill: " + Utility.Rgb2Hex(fontContent) + ";";
        iconsStyle = "-fx-fill: " + Utility.Rgb2Hex(icons) + ";";
        for(Label label : allLabels) {
            label.setStyle(fontStyle);
        }
        for(Label label : allTitles) {
            label.setStyle(fontStyle);
        }
        for(Label label : allSubTitles) {
            label.setStyle(fontStyle);
        }
        for(Separator separator : allDivider) {
            separator.setStyle(dividerContentStyle);
        }
        for(Polygon polygon : allPolygons) {
            polygon.setStyle(backgroundMenuBarStyle);
        }
        for(JFXButton button : allButtons) {
            button.setStyle(buttonsStyle);
        }
        for(AnchorPane pane : panes) {
            pane.setStyle(contentPaneBackgroundStyle);
        }
        for(Polygon polygon : allPolygons) {
            polygon.setStyle(backgroundPolygon);
        }
        for(MaterialDesignIconView icon : allIcons) {
            icon.setStyle(iconsStyle);
        }
        for(MaterialDesignIconView icon : icoMenuBar) {
            icon.setStyle(iconsStyle);
        }
        menuBarPane.setStyle(backgroundMenuBarStyle);
    }

    /**
     *
     * @author Robin Herder
     */
    private void applyTheme() {

    }

    //===[PROCESS INPUT]==================================================

    /**
     *
     * @param pane
     * @param type
     * @author Robin Herder
     */
    public void processItemTypeInput(AnchorPane pane, InventoryType type) {
        String label = ((JFXTextField) pane.getChildren().get(1)).getText();
        logic.addItemType(label, type);
    }

    /**
     *
     * @param pane
     * @author Robin Herder
     */
    public void processFuelTankInput(AnchorPane pane) {
        float capacity = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        int index = ((JFXComboBox<String>) pane.getChildren().get(3)).getSelectionModel().getSelectedIndex();
        logic.addFuelTank(capacity, level, index);
    }

    /**
     *
     * @param pane
     * @author Robin Herder
     */
    public void processGasPumpInput(AnchorPane pane) {
        TableView table = (TableView) pane.getChildren().get(1);
        ArrayList<FuelTank> tanks = new ArrayList<>();
        for(int i = 0; i < table.getItems().size(); i++) {
            tanks.add((FuelTank) table.getItems().get(i));
        }
        logic.addGasPump(tanks);
    }

    /**
     *
     * @param pane
     * @param iType
     * @author Robin Herder
     */
    public void processFuel(AnchorPane pane, ItemType iType){
        float amount = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(3)).getText();
        logic.addFuel(iType, amount, price, currency);
    }

    /**
     *
     * @param pane
     * @param iType
     * @author Robin Herder
     */
    public void processGood(AnchorPane pane, ItemType iType) {
        int amount = Integer.parseInt(((JFXTextField) pane.getChildren().get(1)).getText());
        String unit = ((JFXTextField) pane.getChildren().get(2)).getText();
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(3)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(4)).getText();
        logic.addGood(iType, amount, price, currency, unit);
    }

    public void processGoodCheckout(AnchorPane pane) {
        Item item = (Item) ((TableView) pane.getChildren().get(1)).getSelectionModel().getSelectedItem();
        item.setCheckoutAmount(1);
        if(!tCheckoutSellingOverview.getItems().contains(item)) {
            tCheckoutSellingOverview.getItems().add(item);
        }
        updateCheckoutPrice();
    }

    //===[CHECKOUT SPECIFIC]==================================================

    private void updateCheckoutPrice() {
        float total = 0;
        for(Item item : (ObservableList<Item>) tCheckoutSellingOverview.getItems()) {
            total += item.getPrice() * item.getCheckoutAmount();
        }
        lblTotalSalesOverview.setText(String.valueOf(total));
    }

    private void incAmount() {
        if(tCheckoutSellingOverview.getSelectionModel().getSelectedItem() != null) {
            ArrayList<Item> items = new ArrayList<>();
            items.addAll(tCheckoutSellingOverview.getItems());
            Item item = (Item) tCheckoutSellingOverview.getSelectionModel().getSelectedItem();
            tCheckoutSellingOverview.getItems().clear();
            items.get(items.indexOf(item)).addCheckoutAmount(1);
            tCheckoutSellingOverview.getItems().addAll(items);
            tCheckoutSellingOverview.getSelectionModel().select(item);
            updateCheckoutPrice();
        }
    }

    private void decAmount() {
        if(tCheckoutSellingOverview.getSelectionModel().getSelectedItem() != null) {
            ArrayList<Item> items = new ArrayList<>();
            items.addAll(tCheckoutSellingOverview.getItems());
            Item item = (Item) tCheckoutSellingOverview.getSelectionModel().getSelectedItem();
            tCheckoutSellingOverview.getItems().clear();
            if(items.get(items.indexOf(item)).getCheckoutAmount() > 1) {
                items.get(items.indexOf(item)).addCheckoutAmount(- 1);
            } else {
                items.remove(item);
            }
            tCheckoutSellingOverview.getItems().addAll(items);
            tCheckoutSellingOverview.getSelectionModel().select(item);
            updateCheckoutPrice();
        }
    }

    private void removeElement() {
        if(tCheckoutSellingOverview.getSelectionModel().getSelectedItem() != null) {
            ArrayList<Item> items = new ArrayList<>();
            items.addAll(tCheckoutSellingOverview.getItems());
            Item item = (Item) tCheckoutSellingOverview.getSelectionModel().getSelectedItem();
            tCheckoutSellingOverview.getItems().clear();
            items.remove(item);
            tCheckoutSellingOverview.getItems().addAll(items);
            tCheckoutSellingOverview.getSelectionModel().select(item);
            updateCheckoutPrice();
        }
    }

    private void createReceipt(){
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(tCheckoutSellingOverview.getItems());
        tCheckoutSellingOverview.getItems().clear();
        logic.addReceipt(items);
    }

    //===[CREATE SEARCHABLE DATA]==================================================

    /**
     *
     * @param itemInputDialog
     * @param type
     * @author Robin Herder
     */
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

    public void createGoodsData(GoodsDialog goodsDialog) {
        ObservableList<Good> observableGoodsList = FXCollections.observableArrayList();
        observableGoodsList.addAll(logic.getGoods());
        FilteredList<Good> filteredGoods = new FilteredList<>(observableGoodsList, o -> true);

        goodsDialog.getTxtSearch().textProperty().addListener((observable, oldSearchValue, searchValue) ->
            filteredGoods.setPredicate(Good -> {
                if (searchValue == null || searchValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = searchValue.toLowerCase();
                if (Good.getLABEL().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(Good.getINVENTORY_NUMBER()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            }));

        SortedList<Good> goodSortedList = new SortedList<>(filteredGoods);
        goodSortedList.comparatorProperty().bind(goodsDialog.getTable().comparatorProperty());

        goodsDialog.getTable().setItems(goodSortedList);
    }

    //===[GETTER]==================================================

    /**
     * Gibt dem Style aller Buttons zurück
     * @return buttensStyle
     * @author Robin Herder
     */
    public static String getButtonStyle() {
        return buttonsStyle;
    }

    /**
     * Gibt den Style aller Icons zurück
     * @return iconsStyle
     * @author Robin Herder
     */
    public static String getIconStyle() {
        return iconsStyle;
    }

    /**
     * Gibt die nächste freie Inventarnummer des Types zurück
     * @param type
     * @return freeInvNumber
     * @author Robin Herder
     */
    public int getFreeInvNumber(InventoryType type) {
        return logic.getFreeInvNumber(type);
    }

    /**
     * Gibt die nächste freie Tanknummer zurück
     * @return freeTankNumber
     * @author Robin Herder
     */
    public int getTankNumber() {
        return logic.getFreeTankNumber();
    }

    /**
     * Gibt die Kraftstoffe zurück
     * @return fuel[]
     * @author Robin Herder
     */
    public ArrayList<String> getFuel() {
        return logic.getFuel();
    }

    /**
     *
     * @return rootPane
     * @author Robin Herder
     */
    public StackPane getRootPane() {
        return rootPane;
    }

    /**
     *
     * @param title
     * @return ahja
     * @author Robin Herder
     */
    private String getFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showOpenDialog(rootPane.getScene().getWindow()).getAbsolutePath();
    }

}