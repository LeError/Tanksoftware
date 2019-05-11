package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.Logic;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemTypeInputDialog extends Dialog {

    private WindowController windowCOntroller;

    public ItemTypeInputDialog(StackPane rootPane, WindowController windowController, InventoryType type) {
        this.windowCOntroller = windowController;

        JFXTextField txtInventoryNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(Logic.getInstance().getFreeInvNumber(type)));

        JFXTextField txtLabel = getTextfield(140, 30, false, 60d, 5d, 5d);
        txtLabel.setPromptText("Bezeichner");

        JFXTextField txtType = getTextfield(140, 30, true, 110d, 5d, 5d);
        txtType.setText(type.getTYPE());

        AnchorPane pane = getAnchorPane(160, 150);
        pane.getChildren().addAll(txtInventoryNumber, txtLabel, txtType);

        switch(type.getTYPE()) {
            case "FUEL":
                inputDialog(rootPane, pane, "Erstellen Kraftstofftype");
                break;
            case "GOOD":
                inputDialog(rootPane, pane, "Erstellen Produkttype");
                break;
            default: //TODO raise error
        }
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        windowCOntroller.processItemTypeInput(pane);
    }

}