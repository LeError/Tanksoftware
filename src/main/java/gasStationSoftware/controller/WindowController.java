package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    @FXML private AnchorPane menuBarPane;
    @FXML private ImageView ivUserMenuBar;
    @FXML private MaterialDesignIconView icoSellingMenuBar, icoInventoryMenuBar, icoTanksMenuBar, icoEmployeesMenuBar, icoReportsMenuBar, icoSettingsMenuBar;

    @FXML private AnchorPane userPane;

    @FXML private AnchorPane sellingPane, sellingOverviewPane;

    @FXML private AnchorPane inventoryPane, inventoryOverviewPane, inventoryOrderPane, inventoryDeliveryPane;

    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane;

    @FXML private AnchorPane employeePane, employeeOverviewPane, employeeCreatePane;
    @FXML private TableView tEmployeesEmployeeOverview;

    @FXML private AnchorPane reportPane, reportOverviewPane;

    @FXML private ImageView ivUserProfilePictureUser;

    @FXML private AnchorPane settingsPane, settingsOverviewPane, settingsFuelPane;
    @FXML private JFXButton btnEditThemeSettingsOverview, btnCreateThemeSettingsOverview, btnFuelsSettingsOverview, btnTanksSettingsOverview, btnGasPumpsSettingsOverview, btnExportSettingsOverview, btnImportSettingsOverview, btnNewSettingsView, btnEditSettingsView;
    @FXML private JFXComboBox cbThemeSettingsOverview, cbTypeSettingsOverview;
    @FXML private TableView tFuelsSettingsFuel;

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
        } else if (event.getTarget() == btnGasPumpsSettingsOverview) {
        } else if (event.getTarget() == btnExportSettingsOverview) {
        } else if (event.getTarget() == btnImportSettingsOverview) {
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

    private void addColumnsTFuelsSettingsFuel() {
        TableColumn columnInventoryNumber = new TableColumn("Inventar #");
        columnInventoryNumber.setCellValueFactory(new PropertyValueFactory<>("INVENTORY_NUMBER"));
        TableColumn columnLabel = new TableColumn("Bezeichnung");
        columnLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        TableColumn columnPriceNumber = new TableColumn("Preis");
        columnPriceNumber.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn columnCurrencyNumber = new TableColumn("Währung");
        columnCurrencyNumber.setCellValueFactory(new PropertyValueFactory<>("currency"));
        TableColumn columnAmountNumber = new TableColumn("Menge");
        columnAmountNumber.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tFuelsSettingsFuel.getColumns().addAll(columnInventoryNumber,columnLabel, columnPriceNumber, columnCurrencyNumber, columnAmountNumber);
    }

    public void addRowTEmployeesEmployeeOverview(Employee employee){
        tEmployeesEmployeeOverview.getItems().add(employee);
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
    }

    private void applyTheme() {

    }
}