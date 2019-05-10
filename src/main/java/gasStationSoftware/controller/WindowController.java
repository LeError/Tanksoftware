package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.models.Employee;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.models.GasPump;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
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
    @FXML private JFXButton btnCanelInventoryOrder, btnSubmitInventoryOrder, btnCancelInventoryDelivery, btnImportInventoryDelivery;
    @FXML private MaterialDesignIconView icoOrderInventoryOverview, icoDeliveryInventoryOverview;
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

    @FXML private void handleAction(MouseEvent event) {}

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
            showItemTypeInputDialog(InventoryType.Fuel);
        } else if (event.getTarget() == btnNewSettingsTank) {
            showFuelTankInputDialog();
        } else if (event.getTarget() == btnNewSettingsGasPump) {
            showGasPumpInputDialog();
        } else if (event.getTarget() == btnNewSettingsGood) {
            showItemTypeInputDialog(InventoryType.Good);
        }
    }

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

    private void addColumnsTEmployeesEmployeeOverview() {
        TableColumn columnEmployeeNumber = new TableColumn("Angestelter #");
        columnEmployeeNumber.setCellValueFactory(new PropertyValueFactory<>("EMPLOYEE_NUMBER"));
        TableColumn columnEmploymentDate = new TableColumn("Einstellungsdatum");
        columnEmploymentDate.setCellValueFactory(new PropertyValueFactory<>("EMPLOYMENT_DATE_FORMATTED"));
        TableColumn columnFirstName = new TableColumn("Vorname");
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("FIRST_NAME"));
        TableColumn columnSurname = new TableColumn("Nachname");
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("SUR_NAME"));
        tEmployeesEmployeeOverview.getColumns().addAll(columnEmployeeNumber, columnEmploymentDate, columnFirstName, columnSurname);
    }

    public void addRowTEmployeesEmployeeOverview(Employee employee){
        tEmployeesEmployeeOverview.getItems().add(employee);
    }

    private void addColumnsTFuelsSettingsFuel() {
        tFuelsSettingsFuel.getColumns().addAll(getColumnsItemType());
    }

    public void addRowTFuelsSettingsFuel(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Fuel.getTYPE())) {
            tFuelsSettingsFuel.getItems().add(type);
        }
    }

    private void addColumnsTTanksSettingsTank() {
        TableColumn columnTankNumber = new TableColumn("Tank #");
        columnTankNumber.setCellValueFactory(new PropertyValueFactory<>("TANK_NUMBER"));
        TableColumn columnTankCapacity = new TableColumn("Max Tank Kapazität in l");
        TableColumn columnTankLevel = new TableColumn("Kraftstoff in tank in l");
        columnTankLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        TableColumn columnTankLevelPercentage = new TableColumn("Kraftstoff in tank in %");
        columnTankLevelPercentage.setCellValueFactory(new PropertyValueFactory<>("levelPercentage"));
        columnTankCapacity.setCellValueFactory(new PropertyValueFactory<>("CAPACITY"));
        TableColumn columnTankFuel = new TableColumn("Kraftstoff Type");
        columnTankFuel.setCellValueFactory(new PropertyValueFactory<>("fuelLabel"));
        TableColumn columnInvNumber = new TableColumn("Inventar #");
        columnInvNumber.setCellValueFactory(new PropertyValueFactory<>("invNumber"));
        tTanksSettingsTank.getColumns().addAll(columnTankNumber, columnTankCapacity, columnTankLevel, columnTankLevelPercentage, columnTankFuel, columnInvNumber);
    }

    public void addRowTTanksSettingsTank(FuelTank tank){
        tTanksSettingsTank.getItems().add(tank);
    }

    private void addColumnsTGasPumpsSettingsGasPump() {
        TableColumn columnGasPumpNumber = new TableColumn("Zapfsäule #");
        columnGasPumpNumber.setCellValueFactory(new PropertyValueFactory<>("GAS_PUMP_NUMBER"));
        TableColumn columnGasPumpFuel = new TableColumn("Verfügbare Kraftstoffe");
        columnGasPumpFuel.setCellValueFactory(new PropertyValueFactory<>("assignedFuels"));
        TableColumn columnGasPumpTank = new TableColumn("Angeschlossene Tanks");
        columnGasPumpTank.setCellValueFactory(new PropertyValueFactory<>("assignedTanks"));
        tGasPumpsSettingsGasPump.getColumns().addAll(columnGasPumpNumber, columnGasPumpFuel, columnGasPumpTank);
    }

    public void addRowTGasPumpsSettingsGasPump(GasPump gasPump) {
        tGasPumpsSettingsGasPump.getItems().add(gasPump);
    }


    private void addColumnsTGoodsSettingsGood() {
        tGoodsSettingsGood.getColumns().addAll(getColumnsItemType());
    }

    public void addRowTGoodsSettingsGood(ItemType type) {
        if(type.getTYPE_LABEL().equals(InventoryType.Good.getTYPE())) {
            tGoodsSettingsGood.getItems().add(type);
        }
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

    private void setDefaultContent() {
        cbTypeSettingsOverview.getItems().setAll((Object[]) CB_SETTINGS_TYPE_OPTIONS);
        cbTypeSettingsOverview.setPromptText(CB_SETTINGS_TYPE_PROMT);
    }

    public void setComboboxThemes(String[] themes, String selected) {
        cbThemeSettingsOverview.getItems().setAll((Object[]) themes);
        cbThemeSettingsOverview.setPromptText(selected);
    }

    private void setUser(String firstName, String lastName) {
        //lblUserName.setText(firstName + " " + lastName);
    }

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

    private void showItemTypeInputDialog(InventoryType type) {
        JFXTextField txtInventoryNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(logic.getFreeInvNumber(type)));

        JFXTextField txtLabel = getTextfield(140, 30, false, 60d, 5d, 5d);
        txtLabel.setPromptText("Bezeichner");

        JFXTextField txtType = getTextfield(140, 30, true, 110d, 5d, 5d);
        txtType.setText(type.getTYPE());

        AnchorPane pane = getAnchorPane(160, 150);
        pane.getChildren().addAll(txtInventoryNumber, txtLabel, txtType);

        switch(type.getTYPE()) {
            case "FUEL":
                inputDialog(pane, "Erstellen Kraftstofftype", "ITEM_TYPE");
                break;
            case "GOOD":
                inputDialog(pane, "Erstellen Produkttype", "ITEM_TYPE");
                break;
            default: //TODO raise error
        }
    }

    private void showFuelTankInputDialog() {
        JFXTextField txtInventoryNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(logic.getFreeTankNumber()));

        JFXTextField txtCapacity = getTextfield(140, 30, false, 60d, 5d, 5d);
        txtCapacity.setPromptText("Kapazität in l");

        JFXTextField txtLevel = getTextfield(140, 30, false, 110d, 5d, 5d);
        txtLevel.setPromptText("Füllstand in l");

        JFXComboBox<String> cbFuels = getComboBox(logic.getFuel(), "Kraftstoff wählen", 140, 30, 160d, 5d, 5d);

        AnchorPane pane = getAnchorPane(160, 200);
        pane.getChildren().addAll(txtInventoryNumber, txtCapacity, txtLevel, cbFuels);
        inputDialog(pane, "Erstellen Kraftstofftank", "FUEL_TANK");
    }

    private void showGasPumpInputDialog() {

    }

    @FXML
    private void inputDialog(AnchorPane dialogBodyContent, String title, String inputType){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXButton btnSubmit = new JFXButton("Abschicken");
        btnSubmit.setStyle(buttonsStyle);
        MaterialDesignIconView icoSubmit = new MaterialDesignIconView(MaterialDesignIcon.CHECK);
        icoSubmit.setStyle(iconsStyle);
        btnSubmit.setGraphic(icoSubmit);
        btnSubmit.setOnAction(event -> {
            dialog.close();
            processInput(dialogBodyContent, inputType);
        });

        JFXButton btnCancel = new JFXButton("Abbrechen");
        btnCancel.setStyle(buttonsStyle);
        MaterialDesignIconView icoCancel = new MaterialDesignIconView(MaterialDesignIcon.CLOSE);
        icoCancel.setStyle(iconsStyle);
        btnCancel.setGraphic(icoCancel);
        btnCancel.setOnAction(event -> dialog.close());

        dialogContent.setHeading(new Label(title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnCancel, btnSubmit);
        dialog.show();
    }

    private void processInput(AnchorPane input, String inputType) {
        switch (inputType) {
            case "ITEM_TYPE":
                processItemTypeInput(input);
                break;
            case "FUEL_TANK":
                processFuelTankInput(input);
            default: //TODO raise error
        }
    }

    private void processItemTypeInput(AnchorPane input) {
        switch (((JFXTextField) input.getChildren().get(2)).getText()){
            case "FUEL":
                logic.addItemType(((JFXTextField) input.getChildren().get(1)).getText(), InventoryType.Fuel);
                break;
            case "GOOD":
                logic.addItemType(((JFXTextField) input.getChildren().get(1)).getText(), InventoryType.Good);
                break;
            default: //TODO raise error
        }
    }

    private void processFuelTankInput(AnchorPane input) {
        float capacity = Float.parseFloat(((JFXTextField) input.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) input.getChildren().get(2)).getText());
        logic.addFuelTank(capacity, level, ((JFXComboBox<String>) input.getChildren().get(3)).getSelectionModel().getSelectedIndex());
    }

    private JFXComboBox<String> getComboBox(ArrayList<String> content, String promptText, int prefWidth, int prefHeight, double topAnchor, double rightAnchor, double leftAnchor) {
        JFXComboBox<String> cb = new JFXComboBox<>();
        cb.getItems().addAll(content);
        cb.setPromptText(promptText);
        cb.setPrefSize(prefWidth, prefHeight);
        AnchorPane.setTopAnchor(cb, topAnchor);
        AnchorPane.setRightAnchor(cb, rightAnchor);
        AnchorPane.setLeftAnchor(cb, leftAnchor);
        return cb;
    }

    private JFXTextField getTextfield(int prefWidth, int prefHeight, boolean disable, double topAnchor, double rightAnchor, double leftAnchor) {
        JFXTextField txtField = new JFXTextField();
        txtField.setPrefSize(prefWidth, prefHeight);
        txtField.setDisable(disable);
        AnchorPane.setTopAnchor(txtField, topAnchor);
        AnchorPane.setRightAnchor(txtField, rightAnchor);
        AnchorPane.setLeftAnchor(txtField, leftAnchor);
        return txtField;
    }

    private AnchorPane getAnchorPane(int prefWidth, int prefHeight) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(prefWidth, prefHeight);
        return pane;
    }
}