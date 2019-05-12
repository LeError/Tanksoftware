package gasStationSoftware.ui;

import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StorageUnitInputDialog extends Dialog {

    public StorageUnitInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        AnchorPane pane = getAnchorPane(300, 200);
        inputDialog(rootPane, pane, "Lagereinheit anlegen");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }
}
