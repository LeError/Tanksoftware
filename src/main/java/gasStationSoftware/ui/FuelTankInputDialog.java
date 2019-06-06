package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class FuelTankInputDialog extends Dialog {

    private boolean newEntry = true;

    private JFXTextField txtInventoryNumber, txtCapacity, txtLevel;
    private JFXComboBox<String> cbFuels;

    /**
     * Konstruktor zur erstellung des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param windowController Controller der UI
     * @author Robin Herder
     */
    public FuelTankInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);
        inputDialog(rootPane, create(), "Erstellen Kraftstofftank");
    }

    /**
     * Konstruktor zur erstellung des Dialogs mit werten
     * @param rootPane StackPane der Anwendung
     * @param windowController Controller der UI
     * @param tankID ID des zu editierenden Tanks
     * @param capacity Kapazität des zu editierenden Tanks
     * @param level füllstand des zu editierenden Tanks
     * @param assignedFuel ID des Kraftstoffes des zu editierenden Tanks
     * @author Robin Herder
     */
    public FuelTankInputDialog(StackPane rootPane, WindowController windowController, int tankID, float capacity,
    float level, String assignedFuel) {
        super(windowController);

        AnchorPane pane = create();

        txtInventoryNumber.setDisable(true);
        txtInventoryNumber.setText(String.valueOf(tankID));

        txtCapacity.setText(String.valueOf(capacity));
        txtLevel.setText(String.valueOf(level));

        cbFuels.getSelectionModel().select(assignedFuel);

        newEntry = false;

        inputDialog(rootPane, pane, "Bearbeite Tank");
    }

    /**
     * Startet auswertung des Dialogs
     * @param pane AnchorPane des Dialogs
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        if (newEntry) {
            windowController.processFuelTankInput(pane);
        } else {
            windowController.processExistingFuelTank(pane);
        }
    }

    /**
     * Erstellen der UI Elemente des Dialogs
     * @return pane
     * @author Robin Herder
     */
    private AnchorPane create() {
        txtInventoryNumber = getTextfield(200, 30, true, 10d, 5d, 5d);
        txtInventoryNumber.setText(String.valueOf(windowController.getTankNumber()));

        txtCapacity = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtCapacity.setPromptText("Kapazität in l");

        txtLevel = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtLevel.setPromptText("Füllstand in l");

        cbFuels = getComboBox(windowController.getFuel(), "Kraftstoff wählen", 140, 30, 160d, 5d, 5d);

        AnchorPane pane = getAnchorPane(210, 200);
        pane.getChildren().addAll(txtInventoryNumber, txtCapacity, txtLevel, cbFuels);
        return pane;
    }

}
