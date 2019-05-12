package gasStationSoftware.ui;

import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemDetailInputDialog extends Dialog {

    public ItemDetailInputDialog(StackPane rootPane, WindowController windowController, InventoryType type, ItemType iType) {
        super(windowController);

        AnchorPane pane = getAnchorPane(300, 200);
        if(type == InventoryType.Fuel) {
            inputDialog(rootPane, pane, "Kraftstoff " + iType.getLABEL() + " einbuchen");
        } else if(type == InventoryType.Fuel) {
            inputDialog(rootPane, pane, "Produkt " + iType.getLABEL() + " einbuchen");
        }
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }
}
