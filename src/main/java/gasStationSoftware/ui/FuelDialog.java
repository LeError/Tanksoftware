package gasStationSoftware.ui;

import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class FuelDialog extends Dialog {

    protected FuelDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        inputDialog(rootPane, pane, "Zapfsäule");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }

}
