package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GasPumpDialog extends Dialog {

    private JFXTextField txtSearch;
    private TableView tItemList;

    public GasPumpDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        txtSearch = getTextfield(290, 30, false, 10, 5, 5);
        txtSearch.setPromptText("Suche");

        TableColumn columnInvNumber = getColumn("Säule #", "GAS_PUMP_NUMBER", 90d, false);
        TableColumn columnLabel = getColumn("Produkt", "label", 100d, false);
        TableColumn columnAmount = getColumn("Menge", "checkoutAmount", 98d, false);

        tItemList = getTable(290, 180, 55d, 5d);
        tItemList.getColumns().addAll(columnInvNumber, columnLabel, columnAmount);

        windowController.createGasPumpData(this);

        AnchorPane pane = getAnchorPane(300, 240);
        pane.getChildren().addAll(txtSearch, tItemList);
        inputDialog(rootPane, pane, "Zapfsäule auswählen");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processGasPumpCheckout(pane);
    }

    public JFXTextField getTxtSearch() {
        return txtSearch;
    }

    public TableView getTable() {
        return tItemList;
    }

}
