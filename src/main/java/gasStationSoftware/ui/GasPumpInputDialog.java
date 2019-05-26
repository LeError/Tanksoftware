package gasStationSoftware.ui;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.FuelTank;
import gasStationSoftware.util.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GasPumpInputDialog extends Dialog {

    /**
     * Constructor GasPumpDialog
     * @param rootPane
     * @param windowController
     * @author Robin Herder
     */
    public GasPumpInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        TableColumn columnInvNumber = getColumn("TANK #", "TANK_NUMBER", 60d, false);
        TableColumn columnTank = getColumn("Kraftstoff", "fuelLabel", 138d, false);

        TableView tTankList = getTable(200, 300, 10d, 5d);
        tTankList.getColumns().addAll(columnInvNumber,columnTank);
        windowController.addTankTableRows(tTankList);

        TableColumn columnInvNumberSelected = getColumn("TANK #", "TANK_NUMBER", 60d, false);
        TableColumn columnTankSelected = getColumn("Kraftstoff", "fuelLabel", 138d, false);

        TableView tTankListSelected = getTable(200, 300, 10d, 285d);
        tTankListSelected.getColumns().addAll(columnInvNumberSelected,columnTankSelected);

        JFXButton btnSelectFuel = getButton(30, 30, 100d, 230d);
        btnSelectFuel.setGraphic(getICO(MaterialDesignIcon.CHEVRON_DOUBLE_RIGHT));
        btnSelectFuel.setOnAction(
                event -> {
                    if (tTankList.getSelectionModel().getSelectedItem() != null) {
                        tTankListSelected.getItems().add(tTankList.getSelectionModel().getSelectedItem());
                        tTankList.getItems().remove(tTankList.getSelectionModel().getSelectedItem());
                    }
                });

        JFXButton btnDeselectFuel = getButton(30, 30, 150d, 230d);
        btnDeselectFuel.setGraphic(getICO(MaterialDesignIcon.CHEVRON_DOUBLE_LEFT));
        btnDeselectFuel.setOnAction(event -> {
            if(tTankListSelected.getSelectionModel().getSelectedItem() != null) {
                tTankList.getItems().add(tTankListSelected.getSelectionModel().getSelectedItem());
                tTankListSelected.getItems().remove(tTankListSelected.getSelectionModel().getSelectedItem());
            }
        });

        AnchorPane pane =  getAnchorPane(490, 300);
        pane.getChildren().addAll(tTankList, tTankListSelected, btnSelectFuel, btnDeselectFuel);
        inputDialog(rootPane, pane, "Zapfsäule erstellen");
    }


    /**
     *
     * @param pane
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processGasPumpInput(pane);
    }
}
