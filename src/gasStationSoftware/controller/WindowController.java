package gasStationSoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    private Logic logic;

	private static Color backgroundMenuBar, contentPaneBackground, icons, dividerMenuBar, fontContent, buttonsBackground, buttonsFont, dividerContent;

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

	@FXML private ArrayList<AnchorPane> panes, subPanes;

    @FXML private void handleButtonAction(MouseEvent event) {
        hidePanes();
        if (event.getTarget() == btnUser) {
            userPane.setVisible(true);
        } else if (event.getTarget() == btnSelling) {
            sellingPane.setVisible(true);
			sellingOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnInventory) {
            inventoryPane.setVisible(true);
			inventoryOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnTanks) {
			fuelPane.setVisible(true);
			fuelOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnEmployees) {
            employeePane.setVisible(true);
			employeeOverviewPane.setVisible(true);
        } else if (event.getTarget() == btnReports) {
            reportPane.setVisible(true);
		} else if (event.getTarget() == btnSettings) {
			settingsPane.setVisible(true);
			settingsOverviewPane.setVisible(true);
		} else {
			hideSubPanes();
			if (event.getTarget() == btnInventoryOrder) {
				inventoryOrderPane.setVisible(true);
			} else if (event.getTarget() == btnInventoryDeliveries) {
				inventoryDeliveryPane.setVisible(true);
			} else if (event.getTarget() == btnFuelOrders) {
				fuelOrderPane.setVisible(true);
			} else if (event.getTarget() == btnFuelDeliveries) {
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
