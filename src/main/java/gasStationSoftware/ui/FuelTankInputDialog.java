package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class FuelTankInputDialog extends Dialog {


    public FuelTankInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        JFXTextField txtInventoryNumber = getTextfield(200, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(windowController.getTankNumber()));

        JFXTextField txtCapacity = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtCapacity.setPromptText("Kapazität in l");

        JFXTextField txtLevel = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtLevel.setPromptText("Füllstand in l");

        JFXComboBox<String> cbFuels = getComboBox(windowController.getFuel(), "Kraftstoff wählen", 140, 30, 160d, 5d, 5d);

        AnchorPane pane = getAnchorPane(210, 200);
        pane.getChildren().addAll(txtInventoryNumber, txtCapacity, txtLevel, cbFuels);
        inputDialog(rootPane, pane, "Erstellen Kraftstofftank");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processFuelTankInput(pane);
    }
}
