package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemTypeInputDialog extends Dialog {

    private InventoryType type;

    /**
     * Constructor ItemTypeInputDialog
     * @param rootPane
     * @param windowController
     * @param type
     * @author Robin Herder
     */
    public ItemTypeInputDialog(StackPane rootPane, WindowController windowController, InventoryType type) {
        super(windowController);
        this.type = type;

        JFXTextField txtInventoryNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(windowController.getFreeInvNumber(type)));

        JFXTextField txtLabel = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtLabel.setPromptText("Bezeichner");

        JFXTextField txtType = getTextfield(200, 30, true, 110d, 5d, 5d);
        txtType.setText(type.getTYPE());

        AnchorPane pane = getAnchorPane(210, 150);
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

    /**
     *
     * @param pane
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processItemTypeInput(pane, type);
    }

}