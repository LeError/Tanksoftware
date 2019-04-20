package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    private Logic logic;

	private final static String[] CB_SETTINGS_TYPE_OPTIONS = { "Settings", "Theme", "Inventory" };
	private final static String CB_SETTINGS_TYPE_PROMT = "Type auswählen";

	@FXML private ImageView btnUser, btnSelling, btnInventory, btnTanks, btnEmployees, btnReports, btnSettings;

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

	@FXML private JFXComboBox cbSettingsTheme, cbSettingsType;

	@FXML private Label lblUserName, lblUserRole, lblUserSumDay, lblUserSumMonth, lblUserSumYear, lblUserSumDayCurrency, lblUserSumMonthCurrency, lblUserSumYearCurrency, lblSellingPrice, lblSellingPriceCurrency;

    @FXML private void handleButtonAction(MouseEvent event) {
        hidePanes();
        if (event.getTarget() == btnUser) {
            userPane.setVisible(true);
        } else if (event.getTarget() == btnSelling) {
            sellingPane.setVisible(true);
        } else if (event.getTarget() == btnInventory) {
            inventoryPane.setVisible(true);
			inventoryOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnTanks) {
			fuelPane.setVisible(true);
			fuelOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnEmployees) {
            employeePane.setVisible(true);
        } else if (event.getTarget() == btnReports) {
            reportPane.setVisible(true);
		} else if (event.getTarget() == btnSettings) {
			settingsPane.setVisible(true);
		} else if (event.getTarget() == btnInventoryOrder) {
			hideSubPanes();
			inventoryOrderPane.setVisible(true);
		} else if (event.getTarget() == btnInventoryDeliveries) {
			hideSubPanes();
			inventoryDeliveryPane.setVisible(true);
		} else if (event.getTarget() == btnFuelOrders) {
			hideSubPanes();
			fuelOrderPane.setVisible(true);
		} else if (event.getTarget() == btnFuelDeliveries) {
			hideSubPanes();
			fuelDeliveryPane.setVisible(true);
		}
    }

    private void hidePanes() {
        userPane.setVisible(false);
        sellingPane.setVisible(false);
        inventoryPane.setVisible(false);
		fuelPane.setVisible(false);
        employeePane.setVisible(false);
        reportPane.setVisible(false);
        settingsPane.setVisible(false);
		hideSubPanes();
    }

	private void hideSubPanes() {
		inventoryOverviewPane.setVisible(false);
		inventoryDeliveryPane.setVisible(false);
		inventoryOrderPane.setVisible(false);

		fuelOverviewPane.setVisible(false);
		fuelDeliveryPane.setVisible(false);
		fuelOrderPane.setVisible(false);
	}

    @Override public void initialize(URL location, ResourceBundle resources) {
		logic = new Logic(this);
		setDefaultContent();
	}

	private void setDefaultContent() {
		cbSettingsType.getItems().setAll((Object[]) CB_SETTINGS_TYPE_OPTIONS);
		cbSettingsType.setPromptText(CB_SETTINGS_TYPE_PROMT);
	}

	private void setUser(String firstName, String lastName) {
    	lblUserName.setText(firstName + " " + lastName);
	}
}
