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
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }
}
