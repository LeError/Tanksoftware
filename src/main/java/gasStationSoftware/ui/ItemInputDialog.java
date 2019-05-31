package gasStationSoftware.ui;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;
import gasStationSoftware.util.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ItemInputDialog extends Dialog {

    private InventoryType type;

    private JFXTextField txtSearch;
    private TableView tItemList;
    private StackPane rootPane;

    /**
     * Constructor ItemInputDialog
     * @param rootPane
     * @param windowController
     * @param type
     * @author Robin Herder
     */
    public ItemInputDialog(StackPane rootPane, WindowController windowController, InventoryType type) {
        super(windowController);
        this.type = type;
        this.rootPane = rootPane;

        txtSearch = getTextfield(290, 30, false, 10, 5, 5);
        txtSearch.setPromptText("Suche");

        TableColumn columnInvNumber = getColumn("INV #", "INVENTORY_NUMBER", 90d, false);
        TableColumn columnLabel = getColumn("Produkt", "LABEL", 198d, false);

        tItemList = getTable(290, 180, 55d, 5d);
        tItemList.getColumns().addAll(columnInvNumber,columnLabel);

        windowController.createItemTypeData(this, type);

        AnchorPane pane = getAnchorPane(300, 240);
        pane.getChildren().addAll(txtSearch, tItemList);
        if(type == InventoryType.Fuel) {
            inputDialog(rootPane, pane, "Kraftstoff einbuchen");
        } else if (type == InventoryType.Good) {
            inputDialog(rootPane, pane, "Produkt einbuchen");
        }
    }

    /**
     *
     * @param pane
     * ItemInputDialog
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        ItemType iType = (ItemType) ((TableView) pane.getChildren().get(1)).getSelectionModel().getSelectedItem();
        if(type == InventoryType.Fuel) {
            new ItemFuelDetailInputDialog(rootPane, windowController, iType);
        } else if(type == InventoryType.Good) {
            new ItemGoodDetailInputDialog(rootPane, windowController, iType);
        }
    }

    public JFXTextField getTxtSearch() {
        return txtSearch;
    }

    public TableView getTable() {
        return tItemList;
    }
}
