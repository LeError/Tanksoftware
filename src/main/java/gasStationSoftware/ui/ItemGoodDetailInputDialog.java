package gasStationSoftware.ui;

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

        AnchorPane pane = getAnchorPane(300, 200);
        inputDialog(rootPane, pane, "Produkt " + iType.getLABEL() + " einbuchen");
    }

    @Override protected void processSubmit(AnchorPane pane) {

    }

}
