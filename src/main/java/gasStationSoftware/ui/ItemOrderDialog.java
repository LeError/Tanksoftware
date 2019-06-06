package gasStationSoftware.ui;

import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemOrderDialog extends Dialog {

    protected ItemOrderDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }
}
