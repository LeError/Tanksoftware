package gasStationSoftware.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private ImageView btnUser, btnSelling, btnInventory, btnTanks, btnEmployees, btnReports, btnSettings;

    @FXML
    private AnchorPane userPane, sellingPane, inventoryPane, fuelTankPane, employeePane, reportPane, settingsPane;

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
        //TODO
    }
}