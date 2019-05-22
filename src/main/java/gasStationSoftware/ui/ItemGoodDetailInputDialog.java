package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemGoodDetailInputDialog
extends Dialog {

    private ItemType iType;

    public ItemGoodDetailInputDialog(StackPane rootPane, WindowController windowController, ItemType iType) {
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

        JFXComboBox cbStorage = getComboBox(windowController.getStorageUnit(), "Wähle Lagereinheit", 200, 30, 210d, 5d, 5d);

        AnchorPane pane = getAnchorPane(300, 250);
        pane.getChildren().addAll(txtItem, txtAmount, txtPrice, txtCurrency, cbStorage);
        inputDialog(rootPane, pane, "Produkt " + iType.getLABEL() + " einbuchen");
    }

    @Override protected void processSubmit(AnchorPane pane) {
        windowController.processGood(pane, iType);
    }

}
