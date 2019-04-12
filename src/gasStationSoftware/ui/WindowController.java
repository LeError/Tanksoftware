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
    private AnchorPane userPane, sellingPane, inventoryPane, fuelTankPane, employeePane, reportPane;

    @FXML
    private void handleMenuButtonAction(MouseEvent event) {
        if (event.getTarget() == btnUser) {

        } else if (event.getTarget() == btnSelling) {

        } else if (event.getTarget() == btnInventory) {

        } else if (event.getTarget() == btnTanks) {

        } else if (event.getTarget() == btnEmployees) {

        } else if (event.getTarget() == btnReports) {

        } else if (event.getTarget() == btnSettings) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
