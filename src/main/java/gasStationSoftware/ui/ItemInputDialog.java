package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.Logic;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.util.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemInputDialog extends Dialog {

    private InventoryType type;

    public ItemInputDialog(StackPane rootPane, WindowController windowController, InventoryType type) {
        super(windowController);
        this.type = type;

        JFXTextField txtSearch = getTextfield(290, 30, false, 10, 5, 5);
        txtSearch.setPromptText("Suche");

        TableColumn columnInvNumber = getColumn("INV #", "INVENTORY_NUMBER", 90d, false);
        TableColumn columnLabel = getColumn("Kraftstoff", "LABEL", 198d, false);

        TableView tItemList = getTable(290, 180, 55d, 5d);
        tItemList.getColumns().addAll(columnInvNumber,columnLabel);
        Logic.getInstance().addItemTypeTableRows(tItemList, type);

        AnchorPane pane = getAnchorPane(300, 240);
        pane.getChildren().addAll(txtSearch, tItemList);
        if(type == InventoryType.Fuel) {
            inputDialog(rootPane, pane, "Kraftstoff einbuchen");
        } else if (type == InventoryType.Good) {
            inputDialog(rootPane, pane, "Produkt einbuchen");
        }
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        if(type == InventoryType.Fuel) {

        } else if(type == InventoryType.Good) {

        }
    }
}
