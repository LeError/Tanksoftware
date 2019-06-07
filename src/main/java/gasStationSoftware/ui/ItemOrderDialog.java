package gasStationSoftware.ui;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.Item;
import gasStationSoftware.util.Dialog;
import gasStationSoftware.util.TextFielCustom;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class ItemOrderDialog extends Dialog {

    private TableView tItems, tItemsSelected;
    private InventoryType type;

    public ItemOrderDialog(StackPane rootPane, WindowController windowController, ArrayList<Item> items,
    InventoryType type) {
        super(windowController);
        this.type = type;
        AnchorPane pane = create();
        tItems.getItems().addAll(items);
        if (type == InventoryType.Good) {
            inputDialog(rootPane, pane, "Produkt Order");
        } else {
            inputDialog(rootPane, pane, "Kraftstoff Order");
        }
    }

    @Override
    protected void processSubmit(AnchorPane pane) {

    }

    public AnchorPane create() {
        TableColumn columnInvNumber = getColumn("INV #", "INVENTORY_NUMBER", 60d, false);
        TableColumn columnLabel = getColumn("Item", "LABEL", 138d, false);

        tItems = getTable(200, 300, 10d, 5d);
        tItems.getColumns().addAll(columnInvNumber, columnLabel);

        TableColumn columnInvNumberSelected = getColumn("INV #", "INVENTORY_NUMBER", 60d, false);
        TableColumn columnLabelSelected = getColumn("Item", "LABEL", 138d, false);
        TableColumn<Item, Float> columnAmountSelected = getColumn("Menge", "checkoutAmount", 60d, false);
        columnAmountSelected.setCellFactory(param -> new TextFielCustom());

        tItemsSelected = getTable(260, 300, 10d, 285d);
        tItemsSelected.getColumns().addAll(columnInvNumberSelected, columnLabelSelected, columnAmountSelected);

        JFXButton btnSelectFuel = getButton(30, 30, 100d, 230d);
        btnSelectFuel.setGraphic(getICO(MaterialDesignIcon.CHEVRON_DOUBLE_RIGHT));
        btnSelectFuel.setOnAction(event -> {
            if (tItems.getSelectionModel().getSelectedItem() != null) {
                tItemsSelected.getItems().add(tItems.getSelectionModel().getSelectedItem());
                tItems.getItems().remove(tItems.getSelectionModel().getSelectedItem());
            }
        });

        JFXButton btnDeselectFuel = getButton(30, 30, 150d, 230d);
        btnDeselectFuel.setGraphic(getICO(MaterialDesignIcon.CHEVRON_DOUBLE_LEFT));
        btnDeselectFuel.setOnAction(event -> {
            if (tItemsSelected.getSelectionModel().getSelectedItem() != null) {
                tItems.getItems().add(tItemsSelected.getSelectionModel().getSelectedItem());
                tItemsSelected.getItems().remove(tItemsSelected.getSelectionModel().getSelectedItem());
            }
        });

        AnchorPane pane = getAnchorPane(550, 300);
        pane.getChildren().addAll(tItems, tItemsSelected, btnSelectFuel, btnDeselectFuel);
        return pane;
    }
}
