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

	@FXML private ImageView btnUser, btnSelling, btnInventory, btnTanks, btnEmployees, btnReports, btnSettings;

	@FXML private AnchorPane userPane, sellingPane, inventoryPane, fuelTankPane, employeePane, reportPane, settingsPane;

	@FXML private JFXButton btnSettingsImport, btnSettingsExport, btnSettingsGasPumps, btnSettingsTanks, btnSettingsFuels, btnSettingsCreateTheme, btnSettingsEditTheme, btnSellingCheckOut, btnSellingGoods, btnSellingGasPumps;

	@FXML private JFXComboBox cbSettingsTheme, cbSettingsType;

	@FXML private Label lblUserName, lblUserRole, lblUserSumDay, lblUserSumMonth, lblUserSumYear, lblUserSumDayCurrency, lblUserSumMonthCurrency, lblUserSumYearCurrency, lblSellingPrice, lblSellingPriceCurrency;

    @FXML
    private void handleMenuButtonAction(MouseEvent event) {
        hidePanes();
        if (event.getTarget() == btnUser) {
            userPane.setVisible(true);
        } else if (event.getTarget() == btnSelling) {
            sellingPane.setVisible(true);
        } else if (event.getTarget() == btnInventory) {
            inventoryPane.setVisible(true);
        } else if (event.getTarget() == btnTanks) {
            fuelTankPane.setVisible(true);
        } else if (event.getTarget() == btnEmployees) {
            employeePane.setVisible(true);
        } else if (event.getTarget() == btnReports) {
            reportPane.setVisible(true);
        } else if (event.getTarget() == btnSettings) {
            settingsPane.setVisible(true);
        }
    }

    private void hidePanes() {
        userPane.setVisible(false);
        sellingPane.setVisible(false);
        inventoryPane.setVisible(false);
        fuelTankPane.setVisible(false);
        employeePane.setVisible(false);
        reportPane.setVisible(false);
        settingsPane.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logic = new Logic(this);
    }
}
