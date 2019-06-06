package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemTypeInputDialog extends Dialog {

    private InventoryType type;

    private boolean newEntry = true;

    private JFXTextField txtInventoryNumber, txtLabel, txtType;

    /**
     * Konstruktor zur erstellung des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param windowController Kontroller der UI
     * @param type IventoryType zur differenzierung GOOD / FUEL
     * @author Robin Herder
     */
    public ItemTypeInputDialog(StackPane rootPane, WindowController windowController, InventoryType type) {
        super(windowController);
        this.type = type;

        AnchorPane pane = create();
        txtType.setText(type.getTYPE());

        switch(type.getTYPE()) {
            case "FUEL":
                inputDialog(rootPane, pane, "Erstellen Kraftstofftype");
                break;
        default:
            inputDialog(rootPane, pane, "Erstellen Produkttype");
        }
    }

    /**
     * Konstruktor zur erstellung des Dialogs mit werten
     * @param rootPane StackPane der Anwendung
     * @param windowController Kontroller der UI
     * @param type IventoryType zur differenzierung GOOD / FUEL
     * @param invNumber Invnummer des zu editierenden ItemTypes
     * @param label Label des zu editierenden ItemTypes
     * @author Robin Herder
     */
    public ItemTypeInputDialog(StackPane rootPane, WindowController windowController, InventoryType type, int invNumber,
    String label) {
        super(windowController);

        this.type = type;

        AnchorPane pane = create();
        newEntry = false;

        txtInventoryNumber.setDisable(true);
        txtInventoryNumber.setText(String.valueOf(invNumber));

        txtType.setDisable(true);
        txtType.setText(type.getTYPE());

        txtLabel.setText(label);
        switch (type.getTYPE()) {
        case "FUEL":
            inputDialog(rootPane, pane, "Bearbeite Kraftstofftype");
            break;
        default:
            inputDialog(rootPane, pane, "Bearbeite Produkttype");
        }
    }

    /**
     * Startet auswertung des Dialogs
     * @param pane AnchorPane des Dialogs
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        if (newEntry) {
            windowController.processItemTypeInput(pane, type);
        } else {
            windowController.processExistingItemTypeInput(pane, type);
        }
    }

    /**
     * Erstellen der UI Elemente des Dialogs
     * @return pane
     * @author Robin Herder
     */
    private AnchorPane create() {
        txtInventoryNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(windowController.getFreeInvNumber(type)));

        txtLabel = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtLabel.setPromptText("Bezeichner");

        txtType = getTextfield(200, 30, true, 110d, 5d, 5d);

        AnchorPane pane = getAnchorPane(210, 150);
        pane.getChildren().addAll(txtInventoryNumber, txtLabel, txtType);
        return pane;
    }

}