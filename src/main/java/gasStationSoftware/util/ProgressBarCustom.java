package gasStationSoftware.util;

import gasStationSoftware.models.FuelTank;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;

public class ProgressBarCustom extends TableCell<FuelTank, Double> {

    private ProgressBar progress = new ProgressBar();

    /**
     * Setzt größe
     * @author Robin Herder
     */
    public ProgressBarCustom() {
        progress.setMinWidth(280);
    }

    /**
     * Setzt grafik und wert
     * @author Robin Herder
     */
    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(progress);
            setProgress(item);
        }
    }

    /**
     * setzt progress und styling
     * @author Robin Herder
     */
    public void setProgress(Double value) {
        progress.setProgress(value);
        if(value > 0.5) {
            setStyle("-fx-control-inner-background: #01DF01;");
        } else if(value > 0.25) {
            setStyle("-fx-control-inner-background: #FFFF00;");
        } else {
            setStyle("-fx-control-inner-background: #FB0000;");
        }
    }
}
