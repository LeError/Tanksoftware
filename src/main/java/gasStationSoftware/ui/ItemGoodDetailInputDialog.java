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

    /**
     * Constructor ItemGoodDetailInputDialog
     * @param rootPane
     * @param windowController
     * @param iType
     * @author Robin Herder
     */
    public ItemGoodDetailInputDialog(StackPane rootPane, WindowController windowController, ItemType iType) {
        super(windowController);
        this.iType = iType;

        JFXTextField txtItem = getTextfield(200, 30, true, 10d, 5d, 5d);
        txtItem.setText(iType.getLABEL());

        JFXTextField txtAmount = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtAmount.setText("0");

        JFXTextField txtUnit = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtUnit.setPromptText("Einheit");

        JFXTextField txtPrice = getTextfield(200, 30, false, 160d, 5d, 5d);
        txtPrice.setPromptText("Preis");

        JFXTextField txtCurrency = getTextfield(200, 30, true, 210d, 5d, 5d);
        txtCurrency.setText("EUR");

        JFXComboBox cbStorage = getComboBox(windowController.getStorageUnit(), "Wähle Lagereinheit", 280, 30, 260d, 5d, 5d);

        AnchorPane pane = getAnchorPane(300, 330);
        pane.getChildren().addAll(txtItem, txtAmount, txtUnit, txtPrice, txtCurrency, cbStorage);
        inputDialog(rootPane, pane, "Produkt " + iType.getLABEL() + " einbuchen");
    }

    /**
     *
     * @param pane
     * @author Robin Herder
     */
    @Override protected void processSubmit(AnchorPane pane) {
        windowController.processGood(pane, iType);
    }

}
