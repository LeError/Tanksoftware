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

    @FXML private ImageView ivUserMenuBar, ivUserProfilePictureUser;
    @FXML private MaterialDesignIconView icoSellingMenuBar, icoInventoryMenuBar, icoTanksMenuBar, icoEmployeesMenuBar, icoReportsMenuBar, icoSettingsMenuBar;

    @FXML private AnchorPane userPane;
    @FXML private AnchorPane sellingPane, sellingOverviewPane;
    @FXML private AnchorPane inventoryPane, inventoryOverviewPane, inventoryOrderPane, inventoryDeliveryPane;
    @FXML private AnchorPane fuelPane, fuelOverviewPane, fuelOrderPane, fuelDeliveryPane;
    @FXML private AnchorPane employeePane, employeeOverviewPane;
    @FXML private AnchorPane reportPane, reportOverviewPane;
    @FXML private AnchorPane settingsPane, settingsOverviewPane;

    @FXML private JFXButton btnSellingCheckOut, btnSellingGoods, btnSellingGasPumps;
    @FXML private JFXButton btnInventoryOrder, btnInventoryDeliveries;
    @FXML private JFXButton btnFuelOrders, btnFuelDeliveries;
    @FXML private JFXButton btnSettingsImport, btnSettingsExport, btnSettingsGasPumps, btnSettingsTanks, btnSettingsFuels, btnSettingsCreateTheme, btnSettingsEditTheme;

    @FXML private JFXComboBox cbThemeSettingsOverview, cbTypeSettingsOverview;

    @FXML private Label lblUserName, lblUserRole, lblUserSumDay, lblUserSumMonth, lblUserSumYear, lblUserSumDayCurrency, lblUserSumMonthCurrency, lblUserSumYearCurrency, lblSellingPrice, lblSellingPriceCurrency;

    @FXML private TableView tEmployeesEmployeeOverview;

    @FXML private ArrayList<AnchorPane> panes, subPanes;

    @FXML private void handleButtonAction(MouseEvent event) {
        hidePanes();
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
        } else if (event.getTarget() == icoSettingsMenuBar) {
            settingsPane.setVisible(true);
            settingsOverviewPane.setVisible(true);
        } else {
            hideSubPanes();
            if (event.getTarget() == btnInventoryOrder) {
                inventoryPane.setVisible(true);
                inventoryOrderPane.setVisible(true);
            } else if (event.getTarget() == btnInventoryDeliveries) {
                inventoryPane.setVisible(true);
                inventoryDeliveryPane.setVisible(true);
            } else if (event.getTarget() == btnFuelOrders) {
                fuelPane.setVisible(true);
                fuelOrderPane.setVisible(true);
            } else if (event.getTarget() == btnFuelDeliveries) {
                fuelPane.setVisible(true);
                fuelDeliveryPane.setVisible(true);
            }
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
        addColumnsEmployees();
        setDefaultContent();
    }

    private void addColumnsEmployees() {
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

    public void addColumnTEmployeesEmployeeOverview(Employee employee){
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
        lblUserName.setText(firstName + " " + lastName);
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