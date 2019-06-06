package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemFuelDetailInputDialog
extends Dialog {

    private ItemType iType;

    /**
     * Constructor ItemFuelDetailInputDialog
     * @param rootPane Stackpane det Application
     * @param windowController Controller der UI
     * @param iType ItemType mit dem Gearbeitet wird
     * @author Robin Herder
     */
    public ItemFuelDetailInputDialog(StackPane rootPane, WindowController windowController, ItemType iType) {
        super(windowController);
        this.iType = iType;

        JFXTextField txtItem = getTextfield(200, 30, true, 10d, 5d, 5d);
        txtItem.setText(iType.getLABEL());

        JFXTextField txtAmount = getTextfield(200, 30, true, 60d, 5d, 5d);
        txtAmount.setText("0");

        JFXTextField txtPrice = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtPrice.setPromptText("Preis");

        JFXTextField txtCurrency = getTextfield(200, 30, true, 160d, 5d, 5d);
        txtCurrency.setText("EUR");

        AnchorPane pane = getAnchorPane(210, 200);
        pane.getChildren().addAll(txtItem, txtAmount, txtPrice, txtCurrency);
        inputDialog(rootPane, pane, "Kraftstoff " + iType.getLABEL() + " einbuchen");
    }

    /**
     * Startet auswertung des Dialogs
     * @param pane AnchorPane des Dialogs
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processFuel(pane, iType);
    }
}
