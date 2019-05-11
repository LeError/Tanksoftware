package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.Logic;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class FuelTankInputDialog extends Dialog {


    public FuelTankInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        JFXTextField txtInventoryNumber = getTextfield(200, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(Logic.getInstance().getFreeTankNumber()));

        JFXTextField txtCapacity = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtCapacity.setPromptText("Kapazität in l");

        JFXTextField txtLevel = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtLevel.setPromptText("Füllstand in l");

        JFXComboBox<String> cbFuels = getComboBox(Logic.getInstance().getFuel(), "Kraftstoff wählen", 140, 30, 160d, 5d, 5d);

        AnchorPane pane = getAnchorPane(210, 200);
        pane.getChildren().addAll(txtInventoryNumber, txtCapacity, txtLevel, cbFuels);
        inputDialog(rootPane, pane, "Erstellen Kraftstofftank");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        float capacity = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float level = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        int index = ((JFXComboBox<String>) pane.getChildren().get(3)).getSelectionModel().getSelectedIndex();
        windowController.processFuelTankInput(capacity, level, index);
    }
}
