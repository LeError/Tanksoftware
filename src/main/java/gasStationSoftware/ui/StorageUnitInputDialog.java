package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StorageUnitInputDialog extends Dialog {

    public StorageUnitInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        JFXTextField txtStorageUnitLabel = getTextfield(140, 30, false, 10d, 5d, 5d);
        txtStorageUnitLabel.setPromptText("Bezeichner");

        JFXTextField txtStorageUnitX = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtStorageUnitX.setPromptText("X-Koordinate");

        JFXTextField txtStorageUnitY = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtStorageUnitY.setPromptText("Y-Koordinate");

        AnchorPane pane = getAnchorPane(210, 150);
        pane.getChildren().addAll(txtStorageUnitLabel, txtStorageUnitX, txtStorageUnitY);
        inputDialog(rootPane, pane, "Lagereinheit anlegen");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        String label = ((JFXTextField) pane.getChildren().get(0)).getText();
        int x = Integer.parseInt(((JFXTextField) pane.getChildren().get(1)).getText());
        int y = Integer.parseInt(((JFXTextField) pane.getChildren().get(2)).getText());
        windowController.processStorageUnit(label, x, y);
    }
}
