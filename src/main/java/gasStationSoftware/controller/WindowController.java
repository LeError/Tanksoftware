package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.models.*;
import gasStationSoftware.ui.ContentDialog;
import gasStationSoftware.ui.EmployeeInputDialog;
import gasStationSoftware.ui.FuelTankInputDialog;
import gasStationSoftware.ui.GasPumpDialog;
import gasStationSoftware.ui.GasPumpInputDialog;
import gasStationSoftware.ui.GoodsDialog;
import gasStationSoftware.ui.ItemInputDialog;
import gasStationSoftware.ui.ItemOrderDialog;
import gasStationSoftware.ui.ItemTypeInputDialog;
import gasStationSoftware.ui.ThemeDialog;
import gasStationSoftware.util.Dialog;
import gasStationSoftware.util.ProgressBarCustom;
import gasStationSoftware.util.Utility;
import gasStationSoftware.util.WriteFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class WindowController
implements Initializable {

    private Logic logic;

    private final static String[] CB_SETTINGS_TYPE_OPTIONS = { "Settings", "Theme", "Inventory" };
    private final static String CB_SETTINGS_TYPE_PROMT = "Type auswählen";

    private static Color backgroundMenuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonsBackground, buttonsFont, dividerContent;
    private static String backgroundPolygon, backgroundMenuBarStyle, contentPaneBackgroundStyle, iconsStyle, dividerMenuBarStyle, fontStyle, buttonsStyle, dividerContentStyle;

    @FXML private StackPane rootPane;

    @FXML private AnchorPane loginPane, mainPane;
    @FXML private JFXTextField txtIDLogin;
    @FXML private JFXPasswordField txtPassLogin;
    @FXML private Label lblCopyrightLogin;

    @FXML private AnchorPane menuBarPane;
    @FXML private ImageView ivUserMenuBar;
    @FXML private MaterialDesignIconView icoSellingMenuBar, icoInventoryMenuBar, icoTanksMenuBar, icoEmployeesMenuBar, icoReportsMenuBar, icoSettingsMenuBar;

    @FXML private AnchorPane userPane;
    @FXML private ImageView ivUserProfilePictureUser;
    @FXML private Label lblUserNameUser, lblUserRoleUser, lblSalesSumDailyUser, lblSalesSumMonthlyUser, lblSalesSumYearlyUser;

    @FXML private AnchorPane sellingPane, sellingOverviewPane, sellingReceiptPane;
    @FXML private Label lblTotalSalesOverview;
    @FXML private JFXButton btnCheckOutSalesOverview, btnGoodsSalesOverview, btnGasPumpsSalesOverview, btnAddAmountSalesOverview, btnRemoveAmountSalesOverview, btnDeleteSalesOverview, btnOpenSellingReceipt, btnReceiptSalesOverview;
    @FXML private TableView tCheckoutSellingOverview, tSellingReceipt;

    @FXML private AnchorPane inventoryPane, inventoryOverviewPane, inventoryOrderPane, inventoryDeliveryPane;
    @FXML private JFXButton btnOrderInventoryOverview, btnDeliveriesInventoryOverview, btnAddGoodOverview;
    @FXML private JFXButton btnOpenInventoryOrder, btnCreateInventoryOrder, btnOpeInventoryDelivery, btnImportInventoryDelivery;
    @FXML private TableView tGoodsInventoryOverview, tGoodsInventoryOrder, tGoodsInventoryDelivery;

    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane, tankStatusPane;
    @FXML private JFXButton btnDeliveriesFuelOverview, btnOrdersFuelOverview, btnCreateFuelOrder, btnOpenFuelOrder, btnOpenFuelDeliveries, btnImportFuelDeliveries, btnAddFuelOverview, btnGoToTanks;
    @FXML private TableView tFuelsFuelOverview, tFuelsFuelOrder, tFuelsFuelDeliveries, tTanksStatus;

    @FXML private AnchorPane employeePane, employeeOverviewPane;
    @FXML private JFXButton btnCreateEmployeeOverview, btnEditEmployeeOverview;
    @FXML private TableView tEmployeesEmployeeOverview;

    @FXML private AnchorPane reportPane, reportOverviewPane;
    @FXML private Label lblCostValueReportOverview, lblBalanceValueReportOverview, lblSaleValueReportOverview;
    @FXML private JFXDatePicker dpTimespanReportOverview, dpTimespanReportOverview1;
    @FXML private TableView tReportReportOverview;

    @FXML private AnchorPane settingsPane, settingsOverviewPane, settingsFuelPane, settingsTankPane, settingsGasPumpPane, settingsGoodPane;
    @FXML private JFXTextField txtTitleSettingsOverview;
    @FXML private JFXButton btnEditThemeSettingsOverview, btnCreateThemeSettingsOverview, btnFuelsSettingsOverview, btnTanksSettingsOverview, btnGasPumpsSettingsOverview;
    @FXML private JFXButton btnGoodsSettingsOverview, btnNewSettingsFuel, btnEditSettingsFuel, btnNewSettingsTank;
    @FXML private JFXButton btnEditSettingsTank, btnNewSettingsGasPump, btnEditSettingsGasPump, btnNewSettingsGood, btnEditSettingsGood, btnTitleSettingsOverview;
    @FXML private JFXComboBox cbThemeSettingsOverview;
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
     * Initialisieren des Kontrollers added Standart Content und Tabellenspalten
     * @param location JavaFX Framework übergiebt
     * @param resources JavaFX Framework übergiebt
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
        addColumnsTReportOverview();
        addColumnsTTanksStatus();
        addColumnsTSellingReceipt();
        addColumnsTFuelsFuelOrder();
        addColumnsTGoodsInventoryOrder();
        setDefaultContent();
    }

    //===[HANDLE EVENT]==================================================

    /**
     * Login-Handle logt nutzer ein oder macht login felder rot
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleLoginAction(MouseEvent event) {
        if(!txtPassLogin.getText().equals("") && !txtIDLogin.getText().equals("") && logic.checkLogin(Integer.parseInt(txtIDLogin.getText()), txtPassLogin.getText())) {
            login();
        } else {
            txtIDLogin.setText("");
            txtPassLogin.setText("");
            txtIDLogin.setStyle("-fx-prompt-text-fill: #de1738;");
            txtPassLogin.setStyle("-fx-prompt-text-fill: #de1738;");
        }
    }

    /**
     * Wechsel zwischen den Menus der linken menubar
     * @param event event auslöser
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
     * Handle Verkaufsaktion
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleSaleAction(MouseEvent event) {
        if (event.getTarget() == btnGoodsSalesOverview) {
            new GoodsDialog(rootPane, this);
        } else if(event.getTarget() == btnGasPumpsSalesOverview) {
            new GasPumpDialog(rootPane, this);
        } else if(event.getTarget() == btnCheckOutSalesOverview) {
            if(tCheckoutSellingOverview.getItems().size() > 0) {
                createReceipt();
            }
        } else if (event.getTarget() == btnAddAmountSalesOverview) {
            incAmount();
        } else if (event.getTarget() == btnRemoveAmountSalesOverview) {
            decAmount();
        } else if (event.getTarget() == btnDeleteSalesOverview) {
            removeElement();
        } else if (event.getTarget() == btnReceiptSalesOverview) {
            hideSubPanes();
            sellingReceiptPane.setVisible(true);
        } else if (event.getTarget() == btnOpenSellingReceipt) {
            if (tSellingReceipt.getSelectionModel().getSelectedItem() != null) {
                Document doc = (Document) tSellingReceipt.getSelectionModel().getSelectedItem();
                new ContentDialog(rootPane, this, doc.getNAME(), doc.getLinesForFile(), doc);
            }
        }
    }

    /**
     * Handle Inventoryaktion
     * @param event event auslöser
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
        } else if(event.getTarget() == btnOpeInventoryDelivery) {
            if(tGoodsInventoryDelivery.getSelectionModel().getSelectedItem() != null) {
                Document doc = (Document) tGoodsInventoryDelivery.getSelectionModel().getSelectedItem();
                new ContentDialog(rootPane, this, doc.getNAME(), doc.getLinesForFile(), doc);
            }
        } else if(event.getTarget() == btnOpenInventoryOrder) {
            if(tGoodsInventoryOrder.getSelectionModel().getSelectedItem() != null) {
                Document doc = (Document) tGoodsInventoryOrder.getSelectionModel().getSelectedItem();
                new ContentDialog(rootPane, this, doc.getNAME(), doc.getLinesForFile(), doc);
            }
        } else if(event.getTarget() == btnCreateInventoryOrder) {
            ArrayList<Item> items = new ArrayList<>();
            items.addAll(logic.getGoods());
            new ItemOrderDialog(rootPane, this, items, InventoryType.Good);
        }
    }

    /**
     * Handle Kraftstoffaktion
     * @param event event auslöser
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
        } else if(event.getTarget() == btnGoToTanks) {
            hideSubPanes();
            tankStatusPane.setVisible(true);
        } else if(event.getTarget() == btnOpenFuelDeliveries) {
            if(tFuelsFuelDeliveries.getSelectionModel().getSelectedItem() != null) {
                Document doc = (Document) tFuelsFuelDeliveries.getSelectionModel().getSelectedItem();
                new ContentDialog(rootPane, this, doc.getNAME(), doc.getLinesForFile(), doc);
            }
        } else if(event.getTarget() == btnOpenFuelOrder) {
            if(tFuelsFuelOrder.getSelectionModel().getSelectedItem() != null) {
                Document doc = (Document) tFuelsFuelOrder.getSelectionModel().getSelectedItem();
                new ContentDialog(rootPane, this, doc.getNAME(), doc.getLinesForFile(), doc);
            }
        } else if(event.getTarget() == btnCreateFuelOrder) {
            ArrayList<Item> items = new ArrayList<>();
            items.addAll(logic.getFuels());
            new ItemOrderDialog(rootPane, this, items, InventoryType.Fuel);
        }
    }

    /**
     * Handle Mitarbeiteraktion
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleEmployeeAction(MouseEvent event) {
        if (event.getTarget() == btnEditEmployeeOverview) {
            if(tEmployeesEmployeeOverview.getSelectionModel().getSelectedItem() != null) {
                Employee selected = (Employee) tEmployeesEmployeeOverview.getSelectionModel().getSelectedItem();
                new EmployeeInputDialog(rootPane , this, selected.getEMPLOYEE_NUMBER(), selected.getFIRST_NAME(), selected.getSUR_NAME(), selected.getEMPLOYMENT_DATE(), selected.getIRole());
            }
        } else if (event.getTarget() == btnCreateEmployeeOverview) {
            new EmployeeInputDialog(rootPane, this);
        }
    }

    /**
     * Handle Statistikaktion
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleReportAction(ActionEvent event) {
        logic.updateBalance();
    }

    /**
     * Handle Einstellungsaktion
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleSettingsAction(MouseEvent event) {
        if (event.getTarget() == btnEditThemeSettingsOverview) {
            new ThemeDialog(rootPane, this, backgroundMenuBar, contentPaneBackground, icons, dividerMenuBar,
            fontContent, buttonsBackground, buttonsFont, dividerContent, logic.getThemeTitle());
        } else if (event.getTarget() == btnCreateThemeSettingsOverview) {
            new ThemeDialog(rootPane, this);
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
        } else if (event.getTarget() == btnNewSettingsFuel) {
            new ItemTypeInputDialog(rootPane, this, InventoryType.Fuel);
        } else if (event.getTarget() == btnEditSettingsFuel) {
            if (tFuelsSettingsFuel.getSelectionModel().getSelectedItem() != null) {
                ItemType fuel = (ItemType) tFuelsSettingsFuel.getSelectionModel().getSelectedItem();
                new ItemTypeInputDialog(rootPane, this, InventoryType.Fuel, fuel.getINVENTORY_NUMBER(),
                fuel.getLABEL());
            }
        } else if (event.getTarget() == btnNewSettingsTank) {
            new FuelTankInputDialog(rootPane, this);
        } else if (event.getTarget() == btnEditSettingsTank) {
            if (tTanksSettingsTank.getSelectionModel().getSelectedItem() != null) {
                FuelTank tank = (FuelTank) tTanksSettingsTank.getSelectionModel().getSelectedItem();
                new FuelTankInputDialog(rootPane, this, tank.getTANK_NUMBER(), tank.getCAPACITY(), tank.getLevel(),
                tank.getFuel().getINVENTORY_NUMBER() + ": " + tank.getFuel().getLABEL());
            }
        } else if (event.getTarget() == btnNewSettingsGasPump) {
            new GasPumpInputDialog(rootPane, this);
        } else if (event.getTarget() == btnEditSettingsGasPump) {
            if (tGasPumpsSettingsGasPump.getSelectionModel().getSelectedItem() != null) {
                GasPump pump = (GasPump) tGasPumpsSettingsGasPump.getSelectionModel().getSelectedItem();
                new GasPumpInputDialog(rootPane, this, pump);
            }
        } else if (event.getTarget() == btnNewSettingsGood) {
            new ItemTypeInputDialog(rootPane, this, InventoryType.Good);
        } else if (event.getTarget() == btnEditSettingsGood) {
            if (tGoodsSettingsGood.getSelectionModel().getSelectedItem() != null) {
                ItemType good = (ItemType) tGoodsSettingsGood.getSelectionModel().getSelectedItem();
                new ItemTypeInputDialog(rootPane, this, InventoryType.Good, good.getINVENTORY_NUMBER(), good.getLABEL());
            }
        } else if (event.getTarget() == btnTitleSettingsOverview) {
            if (txtTitleSettingsOverview.getText() != "") {
                logic.setTitle(txtTitleSettingsOverview.getText());
            }
        }
    }

    /**
     * Handle Theme wechssel
     * @param event event auslöser
     * @author Robin Herder
     */
    @FXML private void handleTheme(ActionEvent event) {
        try {
            logic.setTheme((String) cbThemeSettingsOverview.getSelectionModel().getSelectedItem());
        } catch (DataFileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle Fenster schließen
     * @param event
     * @author Robin Herder
     */
    @FXML private void handleCloseAction(MouseEvent event) {
        System.exit(0);
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
        TableColumn columnRole = Dialog.getColumn("Rolle", "role", 200, true);
        tEmployeesEmployeeOverview.getColumns().addAll(columnEmployeeNumber, columnEmploymentDate, columnFirstName, columnSurname, columnRole);
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
     * Spalten der Zapfsäuleneinstellungstabelle einfügen
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
     * Spalten der Kraftstoffübersichtstabelle einfügen
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
     * Spalten der Wareninventarübersichtstabelle einfügen
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

    /**
     * Spalten der Kraftstofflieferungstabelle einfügen
     * @author Robin Herder
     */
    private void addColumnsTFuelsFuelDelivery() {
        TableColumn columnFuelDeliveryName = Dialog.getColumn("Lieferung", "NAME", 200, true);
        TableColumn columnFuelDeliveryDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tFuelsFuelDeliveries.getColumns().addAll(columnFuelDeliveryName, columnFuelDeliveryDate);
    }

    /**
     * Spalten der Warenlieferungstabelle einfügen
     * @author Robin Herder
     */
    private void addColumnsTGoodsInventoryDelivery() {
        TableColumn columnGoodDeliveryName = Dialog.getColumn("Lieferung", "NAME", 200, true);
        TableColumn columnGoodDeliveryDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tGoodsInventoryDelivery.getColumns().addAll(columnGoodDeliveryName, columnGoodDeliveryDate);
    }

    /**
     * Spalten der Verkaufsübersichtstabelle einfügen
     * @author Robin Herder
     */
    private void addColumnsTCheckoutSellingOverview() {
        TableColumn columnCheckoutInvNumber = Dialog.getColumn("INV #","INVENTORY_NUMBER", 100, true);
        TableColumn columnCheckoutLabel = Dialog.getColumn("Bezeichnung","LABEL", 100, true);
        TableColumn columnCheckoutType = Dialog.getColumn("Type","INVENTORY_TYPE", 100, true);
        TableColumn columnCheckoutPrice = Dialog.getColumn("Preis","price", 100, true);
        TableColumn columnCheckoutAmount = Dialog.getColumn("Anz","checkoutAmount", 100, true);
        tCheckoutSellingOverview.getColumns().addAll(columnCheckoutInvNumber, columnCheckoutLabel, columnCheckoutType, columnCheckoutPrice, columnCheckoutAmount);
    }

    /**
     * Spalten der Verkaufsübersichtstabelle einfügen
     * @author Robin Herder
     */
    private void addColumnsTReportOverview() {
        TableColumn columnReportDate = Dialog.getColumn("Datum", "DATE", 100, true);
        TableColumn columnReportName = Dialog.getColumn("Name", "NAME", 200, true);
        TableColumn columnReportType = Dialog.getColumn("Type", "DOC_TYPEForTab", 200, true);
        TableColumn columnReportTotal = Dialog.getColumn("Wert", "totalForTab", 100, true);
        tReportReportOverview.getColumns().addAll(columnReportDate, columnReportName, columnReportType, columnReportTotal);
    }

    /**
     * Spalten der Tankübersicht einfügen
     * @author Robin Herder
     */
    private void addColumnsTTanksStatus() {
        TableColumn columnTankNumber = Dialog.getColumn("Tank #", "TANK_NUMBER", 100, true);
        TableColumn columnTankFuel = Dialog.getColumn("Kraftstoff #", "fuelLabel", 200, true);
        TableColumn columnLevel = Dialog.getColumn("Füllstand in %", "levelPercentage", 100, true);
        TableColumn<FuelTank, Double> columnTankProgress= Dialog.getColumn("Tank #", "levelPercentageProgress", 300, true);
        columnTankProgress.setCellFactory(param -> new ProgressBarCustom());
        tTanksStatus.getColumns().addAll(columnTankNumber, columnTankFuel, columnLevel, columnTankProgress);
    }

    /**
     * Spalten der Quittungstabelle
     * @author Robin Herder
     */
    private void addColumnsTSellingReceipt() {
        TableColumn columnReceiptName = Dialog.getColumn("Quittung", "NAME", 200, true);
        TableColumn columnReceiptDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tSellingReceipt.getColumns().addAll(columnReceiptName, columnReceiptDate);
    }

    /**
     * Spalten der Kraftstoffbestellungstabelle
     * @author Robin Herder
     */
    public void addColumnsTFuelsFuelOrder() {
        TableColumn columnFuelOrderName = Dialog.getColumn("Bestellung", "NAME", 200, true);
        TableColumn columnFuelOrderDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tFuelsFuelOrder.getColumns().addAll(columnFuelOrderName, columnFuelOrderDate);
    }

    /**
     * Spalten der Produktbestellungstabelle
     * @author Robin Herder
     */
    public void addColumnsTGoodsInventoryOrder() {
        TableColumn columnGoodOrderName = Dialog.getColumn("Bestellung", "NAME", 200, true);
        TableColumn columnGoodOrderDate = Dialog.getColumn("Datum", "DATE", 200, true);
        tGoodsInventoryOrder.getColumns().addAll(columnGoodOrderName, columnGoodOrderDate);
    }

    /**
     * Spalten der SettingsItemTypetablle einfügen
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
     * @param employee Angestelten objekt liste
     * @author Robin Herder
     */
    public void addRowTEmployeesEmployeeOverview(ArrayList<Employee> employee){
        tEmployeesEmployeeOverview.getItems().clear();
        tEmployeesEmployeeOverview.getItems().addAll(employee);
    }

    /**
     * Neuer Kraftstoff der Kraftstoffeinstellungen-Tabelle hinzufügen
     * @param types Produkt/Kraftstofftype objekt liste
     * @author Robin Herder
     */
    public void addRowTFuelsSettingsFuel(ArrayList<ItemType> types) {
        types = Utility.getInventoryType(types, InventoryType.Fuel);
        tFuelsSettingsFuel.getItems().clear();
        tFuelsSettingsFuel.getItems().addAll(types);
    }

    /**
     * Neuer Tank der Tankeinstellungen-Tabelle hinzufügen
     * @param tanks Tank objekt liste
     * @author Robin Herder
     */
    public void addRowTTanksSettingsTank(ArrayList<FuelTank> tanks){
        tTanksSettingsTank.getItems().clear();
        tTanksSettingsTank.getItems().addAll(tanks);
        addRowTTankStatus(tanks);
    }

    /**
     * Neue Zapfsäuler der Zapfsäuleneinstellung-Tabelle hinzufügen
     * @param gasPumps Zapfsäulen objekt liste
     * @author Robin Herder
     */
    public void addRowTGasPumpsSettingsGasPump(ArrayList<GasPump> gasPumps) {
        tGasPumpsSettingsGasPump.getItems().clear();
        tGasPumpsSettingsGasPump.getItems().addAll(gasPumps);
    }

    /**
     * Neue Ware der Wareneinstellungen-Tabelle hinzufügen
     * @param types Produkt/Kraftstofftype objekt liste
     * @author Robin Herder
     */
    public void addRowTGoodsSettingsGood(ArrayList<ItemType> types) {
        types = Utility.getInventoryType(types, InventoryType.Good);
        tGoodsSettingsGood.getItems().clear();
        tGoodsSettingsGood.getItems().addAll(types);
    }

    /**
     * Kraftstoff der Kraftstoffübersichtstabelle hinzufügen
     * @param fuels Kraftstoff objekt liste
     * @author Robin Herder
     */
    public void addRowTFuelsFuelOverview(ArrayList<Fuel> fuels) {
        tFuelsFuelOverview.getItems().clear();
        tFuelsFuelOverview.getItems().addAll(fuels);
    }

    /**
     * Ware der Warenübersichtstabelle hinzufügen
     * @param goods Produkt objekt liste
     * @author Robin Herder
     */
    public void addRowTGoodsInventoryOverview(ArrayList<Good> goods) {
        tGoodsInventoryOverview.getItems().clear();
        tGoodsInventoryOverview.getItems().addAll(goods);
    }

    /**
     * Lieferungen der Kraftstofflieferungstabelle hinzufügen
     * @param deliveries Kraftstofflieferung objekt liste
     * @author Robin Herder
     */
    public void addRowTFuelsFuelDelivery(ArrayList<FuelDeliveryDocument> deliveries) {
        tFuelsFuelDeliveries.getItems().clear();
        tFuelsFuelDeliveries.getItems().addAll(deliveries);
    }

    /**
     * Lieferungen der Produktlieferungstabelle hinzufügen
     * @param deliveries Produktliefrungd objekt liste
     * @author Robin Herder
     */
    public void addRowTGoodsInventoryDelivery(ArrayList<GoodDeliveryDocument> deliveries) {
        tGoodsInventoryDelivery.getItems().clear();
        tGoodsInventoryDelivery.getItems().addAll(deliveries);
    }

    /**
     * Rechungen oder Quittungen zu Balancetabelle hinzufügen
     * @param documents Dokument objekt liste
     * @author Robin Herder
     */
    public void addRowTReportReportOverview(ArrayList<Document> documents) {
        tReportReportOverview.getItems().clear();
        tReportReportOverview.getItems().addAll(documents);
    }

    /**
     * Füllstandmenge
     * @param tanks Tank objekt liste
     * @author Robin Herder
     */
    public void addRowTTankStatus(ArrayList<FuelTank> tanks) {
        tTanksStatus.getItems().clear();
        tTanksStatus.getItems().addAll(tanks);
    }

    /**
     * Quittungen zu Quittungstabelle hinzufügen
     * @param receipt liste der quittungen
     * @author Robin Herder
     */
    public void addRowTSellingReceipt(ArrayList<CustomerOrder> receipt) {
        tSellingReceipt.getItems().clear();
        tSellingReceipt.getItems().addAll(receipt);
    }

    /**
     * Bestellung zu Bestellungstabelle hinzufügen
     * @param fuelOrders liste der quittungen
     * @author Robin Herder
     */
    public void addRowTFuelsFuelOrder(ArrayList<FuelOrderDocument> fuelOrders) {
        tFuelsFuelOrder.getItems().clear();
        tFuelsFuelOrder.getItems().addAll(fuelOrders);
    }

    /**
     * Bestellung zu Bestellungstabelle hinzufügen
     * @param goodOrders liste der quittungen
     * @author Robin Herder
     */
    public void addRowTGoodsInventoryOrder(ArrayList<GoodOrderDocument> goodOrders) {
        tGoodsInventoryOrder.getItems().clear();
        tGoodsInventoryOrder.getItems().addAll(goodOrders);
    }

    //===[LOGIC CALL]==================================================

    /**
     * Tabelle an logic weiterreichen
     * @param table Tabelle der Tankansicht
     * @author Robin Herder
     */
    public void addTankTableRows(TableView table) {
        logic.addTankTableRows(table);
    }

    //===[DEFAULT CONTENT]==================================================

    /**
     * Setzen von Standart elementen
     * @author Robin Herder
     */
    private void setDefaultContent() {
        lblCopyrightLogin.setText("\u00a9 Lea Buchhold, Lukas Fink, Malik Press, Robin Herder 2019");
    }

    /**
     * Combobox die themes übergeben und das aktive auswählen
     * @param themes Array mit den möglichen themes
     * @param selected ausgewähltes theme
     * @author Robin Herder
     */
    public void setComboboxThemes(String[] themes, String selected) {
        cbThemeSettingsOverview.getItems().setAll((Object[]) themes);
        cbThemeSettingsOverview.getSelectionModel().select(selected);
    }

    //===[THEME]==================================================

    /**
     * Theme auf UI anwenden
     * @param backgroundMenuBar Farb objekt für theme
     * @param contentPaneBackground Farb objekt für theme
     * @param icons Farb objekt für theme
     * @param dividerMenuBar Farb objekt für theme
     * @param fontContent Farb objekt für theme
     * @param buttonsBackground Farb objekt für theme
     * @param buttonsFont Farb objekt für theme
     * @param dividerContent Farb objekt für theme
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

    //===[PROCESS INPUT]==================================================

    /**
     * Auswerten des ItemTypeInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param type InventoryType des dialogs
     * @author Robin Herder
     */
    public void processItemTypeInput(AnchorPane pane, InventoryType type) {
        String label = ((JFXTextField) pane.getChildren().get(1)).getText();
        logic.addItemType(label, type);
    }

    /**
     * Auswerten des ItemTypeInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param type InventoryType des dialogs
     * @author Robin Herder
     */
    public void processExistingItemTypeInput(AnchorPane pane, InventoryType type) {
        int id = Integer.parseInt(((JFXTextField) pane.getChildren().get(0)).getText());
        String label = ((JFXTextField) pane.getChildren().get(1)).getText();
        logic.editItemType(id, label, type);
    }

    /**
     * Auswerten des FuelTankInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processFuelTankInput(AnchorPane pane) {
        float capacity = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        int index = ((JFXComboBox<String>) pane.getChildren().get(3)).getSelectionModel().getSelectedIndex();
        logic.addFuelTank(capacity, level, index);
    }

    /**
     * Auswerten des FuelTankInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processExistingFuelTank(AnchorPane pane) {
        int id = Integer.parseInt(((JFXTextField) pane.getChildren().get(0)).getText());
        float capacity = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        int index = ((JFXComboBox<String>) pane.getChildren().get(3)).getSelectionModel().getSelectedIndex();
        logic.editFuelTank(id, capacity, level, index);
    }

    /**
     * Auswerten des GasPumpInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
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
     * Auswerten des GasPumpInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param id id der zu Editierenden Zapfsäule
     * @author Robin Herder
     */
    public void processExistingGasPump(AnchorPane pane, int id) {
        TableView table = (TableView) pane.getChildren().get(1);
        ArrayList<FuelTank> tanks = new ArrayList<>();
        for(int i = 0; i < table.getItems().size(); i++) {
            tanks.add((FuelTank) table.getItems().get(i));
        }
        logic.editGasPump(tanks, id);
    }

    /**
     * Auswerten des FuelInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param iType ItemType des anzulegenden Kraftstoffes
     * @author Robin Herder
     */
    public void processFuel(AnchorPane pane, ItemType iType){
        float amount = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(3)).getText();
        logic.addFuel(iType, amount, price, currency);
    }

    /**
     * Auswerten des GoodInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param iType ItemType des anzulegenden Produkts
     * @author Robin Herder
     */
    public void processGood(AnchorPane pane, ItemType iType) {
        int amount = Integer.parseInt(((JFXTextField) pane.getChildren().get(1)).getText());
        String unit = ((JFXTextField) pane.getChildren().get(2)).getText();
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(3)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(4)).getText();
        logic.addGood(iType, amount, price, currency, unit);
    }

    /**
     * Auswerten des GoodsDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processGoodCheckout(AnchorPane pane) {
        Item item = (Item) ((TableView) pane.getChildren().get(1)).getSelectionModel().getSelectedItem();
        if(((Good) item).getAmount() > 0) {
            item.setCheckoutAmount(1);
            if(!tCheckoutSellingOverview.getItems().contains(item)) {
                tCheckoutSellingOverview.getItems().add(item);
            }
            updateCheckoutPrice();
        }
    }

    /**
     * Auswerten des GasPumpsDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processGasPumpCheckout(AnchorPane pane) {
        GasPump selectedGasPump = (GasPump) ((TableView) pane.getChildren().get(1)).getSelectionModel().getSelectedItem();
        Item item = selectedGasPump.getCheckoutFuel();
        item.setCheckoutAmount(selectedGasPump.getCheckoutAmount());
        ((Fuel) item).setCheckoutTank(selectedGasPump);
        if(!tCheckoutSellingOverview.getItems().contains(item)) {
            tCheckoutSellingOverview.getItems().add(item);
        }
        updateCheckoutPrice();
    }

    /**
     * Auswerten des EmployeeInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processEmployee(AnchorPane pane) {
        String firstName = ((JFXTextField) pane.getChildren().get(1)).getText();
        String surName = ((JFXTextField) pane.getChildren().get(2)).getText();
        Date employmentDate = Date.from(((JFXDatePicker) pane.getChildren().get(3)).getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String userRole = (String) ((JFXComboBox) pane.getChildren().get(4)).getSelectionModel().getSelectedItem();
        String userPass = ((JFXPasswordField) pane.getChildren().get(5)).getText();
        logic.addEmployee(firstName, surName, employmentDate, userRole, userPass);
    }

    /**
     * Auswerten des EmployeeInputDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @author Robin Herder
     */
    public void processExistingEmployee(AnchorPane pane) {
        String employeeNumber = ((JFXTextField) pane.getChildren().get(0)).getText();
        String firstName = ((JFXTextField) pane.getChildren().get(1)).getText();
        String surName = ((JFXTextField) pane.getChildren().get(2)).getText();
        String userRole = (String) ((JFXComboBox) pane.getChildren().get(4)).getSelectionModel().getSelectedItem();
        String userPass = ((JFXPasswordField) pane.getChildren().get(5)).getText();
        logic.editEmployee(Integer.parseInt(employeeNumber), firstName, surName, userRole, userPass);
    }

    /**
     * Auswerten des ThemeDialogs und daten weitergabe an logic
     * @author Robin Herder
     * @param pane Anchorpane des dialogs
     */
    public void processThemeInput(AnchorPane pane) {
        Color menuBar = new Color((float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getOpacity());
        Color contentPaneBackground = new Color((float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getOpacity());
        Color icons = new Color((float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getOpacity());
        Color dividerMenuBar = new Color((float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getOpacity());
        Color fontContent = new Color((float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getOpacity());
        Color buttonBackground = new Color((float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getOpacity());
        Color buttonFont = new Color((float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getOpacity());
        Color dividerContent = new Color((float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getOpacity());
        String themeTitle = ((JFXTextField) pane.getChildren().get(16)).getText();
        logic.saveTheme(menuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonBackground, buttonFont, dividerContent, themeTitle);
    }

    /**
     * Auswerten des ThemeDialogs und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param title title des zu editierenden themes
     * @author Robin Herder
     */
    public void processExistingTheme(AnchorPane pane, String title) {
        Color menuBar = new Color((float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(8)).getValue().getOpacity());
        Color contentPaneBackground = new Color((float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(9)).getValue().getOpacity());
        Color icons = new Color((float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(10)).getValue().getOpacity());
        Color dividerMenuBar = new Color((float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(11)).getValue().getOpacity());
        Color fontContent = new Color((float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(12)).getValue().getOpacity());
        Color buttonBackground = new Color((float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(13)).getValue().getOpacity());
        Color buttonFont = new Color((float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(14)).getValue().getOpacity());
        Color dividerContent = new Color((float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getRed(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getGreen(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getBlue(), (float) ((JFXColorPicker) pane.getChildren().get(15)).getValue().getOpacity());
        logic.saveTheme(menuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonBackground, buttonFont, dividerContent, title);
    }

    /**
     * Auswerten des ItemOrderDialog und daten weitergabe an logic
     * @param pane Anchorpane des dialogs
     * @param type type des belegs
     * @author Robin Herder
     */
    public void processOrder(AnchorPane pane, InventoryType type) {
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(((TableView) pane.getChildren().get(1)).getItems());
        logic.addOrder(items, type);
    }

    //===[CHECKOUT SPECIFIC]==================================================

    /**
     * Aktualisierung des angezeigten Gesamtpreises
     * @author Robin Herder
     */
    private void updateCheckoutPrice() {
        float total = 0;
        for(Item item : (ObservableList<Item>) tCheckoutSellingOverview.getItems()) {
            total += item.getPrice() * item.getCheckoutAmount();
        }
        lblTotalSalesOverview.setText(String.valueOf(Utility.round(total, 2)));
    }

    /**
     * Erhöhen der anzahl des ausgewählten elements im checkout
     * @author Robin Herder
     */
    private void incAmount() {
        if(tCheckoutSellingOverview.getSelectionModel().getSelectedItem() != null && ((Good) tCheckoutSellingOverview.getSelectionModel().getSelectedItem()).getAmount() > ((Good) tCheckoutSellingOverview.getSelectionModel().getSelectedItem()).getCheckoutAmount()) {
            if((tCheckoutSellingOverview.getSelectionModel().getSelectedItem()) instanceof Good) {
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
    }

    /**
     * Ernidirgen der anzahl des ausgewählten elements im checkout
     * @author Robin Herder
     */
    private void decAmount() {
        if(tCheckoutSellingOverview.getSelectionModel().getSelectedItem() != null) {
            if((tCheckoutSellingOverview.getSelectionModel().getSelectedItem()) instanceof Good) {
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
    }

    /**
     * Entfernen der anzahl des ausgewählten elements im checkout
     * @author Robin Herder
     */
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

    /**
     * Triggert das erstellen einer Quittung
     * @author Robin Herder
     */
    private void createReceipt(){
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(tCheckoutSellingOverview.getItems());
        tCheckoutSellingOverview.getItems().clear();
        logic.addReceipt(items);
        lblTotalSalesOverview.setText("0.00");
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

    /**
     * Ersetllen der filterfunktionalität des GoodsDialog
     * @param goodsDialog der goodsDialog um zurück zugreifen zu können
     * @author Robin Herder
     */
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

    /**
     * Ersetllen der filterfunktionalität des GasPumpDialog
     * @param gasPumpDialog der gasPumpDialog um zurück zugreifen zu können
     * @author Robin Herder
     */
    public void createGasPumpData(GasPumpDialog gasPumpDialog) {
        ObservableList<GasPump> observableGasPumpList = FXCollections.observableArrayList();
        observableGasPumpList.addAll(logic.getUsedGasPumps());
        FilteredList<GasPump> filteredGasPumps = new FilteredList<>(observableGasPumpList, o -> true);

        gasPumpDialog.getTxtSearch().textProperty().addListener((observable, oldSearchValue, searchValue) ->
                filteredGasPumps.setPredicate(gasPump -> {
                    if (searchValue == null || searchValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = searchValue.toLowerCase();
                    if (gasPump.getCheckoutFuel().getLABEL().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(gasPump.getGAS_PUMP_NUMBER()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));

        SortedList<GasPump> gasPumpSortedList = new SortedList<>(filteredGasPumps);
        gasPumpSortedList.comparatorProperty().bind(gasPumpDialog.getTable().comparatorProperty());
        gasPumpDialog.getTable().setItems(gasPumpSortedList);
    }

    //===[GETTER]==================================================

    /**
     * Getter für die Dates der DatePicker der Reportoberfläche
     * @return dates
     * @author Robin Herder
     */
    public ArrayList<Date> getReportDates() {
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(Date.from(dpTimespanReportOverview.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dates.add(Date.from(dpTimespanReportOverview1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return dates;
    }

    /**
     * Gibt den Style aller Buttons zurück (CSS-String)
     * @return buttensStyle
     * @author Robin Herder
     */
    public static String getButtonStyle() {
        return buttonsStyle;
    }

    /**
     * Gibt den Style aller Icons zurück (CSS-String)
     * @return iconsStyle
     * @author Robin Herder
     */
    public static String getIconStyle() {
        return iconsStyle;
    }

    /**
     * Gibt die nächste freie Inventarnummer des InventoryTypes zurück
     * @param type
     * @return freeInvNumber
     * @author Robin Herder
     */
    public int getFreeInvNumber(InventoryType type) {
        return logic.getFreeInvNumber(type);
    }

    /**
     * Gibt die nächste freie Inventarnummer für Angestellte zurück zurück
     * @return
     * @author Robin Herder
     */
    public int getFreeEmployeeNumber() {
        return logic.getFreeEmployeeNumber();
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
     * Gibt die StackPane (rootPane) der anwendung zurück
     * @return rootPane
     * @author Robin Herder
     */
    public StackPane getRootPane() {
        return rootPane;
    }

    /**
     * Gibt die Nutzerrollen als String zurück
     * @return
     * @author Robin Herder
     */
    public ArrayList<String> getUserRoles() {
        return logic.getUserRoles();
    }

    /**
     * Gibt einen Pfad als String zurück über einen FileChooser
     * @param title Title des FileChoosers
     * @return path
     * @author Robin Herder
     */
    private String getFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showOpenDialog(rootPane.getScene().getWindow()).getAbsolutePath();
    }

    /**
     * Gibt einen Pfad als String zurück über einen FileChooser
     * @param title Title des FileChoosers
     * @return path
     * @author Robin Herder
     */
    private String getFileSave(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showSaveDialog(rootPane.getScene().getWindow()).getAbsolutePath();
    }

    /**
     * Loginscreen initialisieren je nach rechterolle
     * @author Robin Herder
     */
    private void login() {
        hidePanes();
        hideSubPanes();
        loginPane.setVisible(false);
        mainPane.setVisible(true);
        menuBarPane.setVisible(true);
        userPane.setVisible(true);
        lblUserNameUser.setText(logic.getEmployeeName());
        lblUserRoleUser.setText(logic.getEmployeeRole());

        if(logic.getRoleID() > 0) {
            for(AnchorPane pane : panes) {
                pane.setDisable(true);
            }
            for(AnchorPane pane : subPanes) {
                pane.setDisable(true);
            }
            if(logic.getRoleID() == 1) {
                userPane.setDisable(false);
                sellingPane.setDisable(false);
                inventoryPane.setDisable(false);
                fuelPane.setDisable(false);
                sellingOverviewPane.setDisable(false);
                inventoryOverviewPane.setDisable(false);
                inventoryOrderPane.setDisable(false);
                inventoryDeliveryPane.setDisable(false);
                fuelOverviewPane.setDisable(false);
                fuelOrderPane.setDisable(false);
                fuelDeliveryPane.setDisable(false);
            } else if(logic.getRoleID() == 2) {
                userPane.setDisable(false);
                sellingPane.setDisable(false);
                sellingOverviewPane.setDisable(false);
            }
        }
    }

    /**
     * Bilanz aktualisieren (werte)
     * @param documents liste der anzuzeigenden Dokumente
     * @param cost Insgesamte ausgaben
     * @param sale Insgesamte einnahmen
     * @param balance einnahmen - ausgaben
     * @author Robin Herder
     */
    public void updateBalance(ArrayList<Document> documents, float cost, float sale, float balance) {
        lblCostValueReportOverview.setText("- " + cost);
        lblSaleValueReportOverview.setText("+ " + sale);
        lblBalanceValueReportOverview.setText(String.valueOf(balance));
        addRowTReportReportOverview(documents);
    }

    /**
     * Mitarbeiterbilanz aktualisieren
     * @param day Tageseinnahmen des Angestelten
     * @param month Monatseinnahmen des Angestellten
     * @param year Jahreseinnahmen des Angestellten
     * @author Robin Herder
     */
    public void updateEmployeeBalance(float day, float month, float year) {
        lblSalesSumDailyUser.setText(String.valueOf(day));
        lblSalesSumMonthlyUser.setText(String.valueOf(month));
        lblSalesSumYearlyUser.setText(String.valueOf(year));
    }

    /**
     * Überprüft ob einer der DatePicker in der Bilanz kein Datum ausgewählt hat
     * @return datePickerNotInitial
     * @author Robin Herder
     */
    public boolean noTimeSpan() {
        return dpTimespanReportOverview.getValue() == null || dpTimespanReportOverview1.getValue() == null;
    }

    /**
     * Titel auf UI setzen
     * @author Robin Herder
     */
    public void setTitle(String title) {
        txtTitleSettingsOverview.setText(title);
    }

    /**
     * Profibild in UI setzen
     * @author Robin Herder
     */
    public void setProfilePicture(Image image) {
        ivUserMenuBar.setImage(image);
        ivUserProfilePictureUser.setImage(image);
    }

    public void export(Document doc) throws IOException {
        WriteFile write = new WriteFile(getFileSave("Exportieren"));
        for(String line : doc.getLinesForFile()) {
            write.addLine(line);
        }
        write.write();
    }

}