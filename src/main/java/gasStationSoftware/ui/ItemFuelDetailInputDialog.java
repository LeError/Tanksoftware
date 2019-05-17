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

    @Override
    protected void processSubmit(AnchorPane pane) {
        float amount = Float.parseFloat(((JFXTextField) pane.getChildren().get(1)).getText());
        float price = Float.parseFloat(((JFXTextField) pane.getChildren().get(2)).getText());
        String currency = ((JFXTextField) pane.getChildren().get(3)).getText();
        windowController.processFuel(iType, amount, price, currency);
    }
}
