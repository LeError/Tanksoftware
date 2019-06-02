package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GoodsDialog extends Dialog {

    private JFXTextField txtSearch;
    private TableView tItemList;

    public GoodsDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        txtSearch = getTextfield(290, 30, false, 10, 5, 5);
        txtSearch.setPromptText("Suche");

        TableColumn columnInvNumber = getColumn("INV #", "INVENTORY_NUMBER", 90d, false);
        TableColumn columnLabel = getColumn("Produkt", "LABEL", 198d, false);

        tItemList = getTable(290, 180, 55d, 5d);
        tItemList.getColumns().addAll(columnInvNumber,columnLabel);

        windowController.createGoodsData(this);

        AnchorPane pane = getAnchorPane(300, 240);
        pane.getChildren().addAll(txtSearch, tItemList);
        inputDialog(rootPane, pane, "Produkt Auswahl");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processGoodCheckout(pane);
    }

    public JFXTextField getTxtSearch() {
        return txtSearch;
    }

    public TableView getTable() {
        return tItemList;
    }

}
