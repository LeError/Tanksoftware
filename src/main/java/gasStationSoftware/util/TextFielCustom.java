package gasStationSoftware.util;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.models.Item;
import javafx.scene.control.TableCell;

public class TextFielCustom extends TableCell<Item, Double> {
    private JFXTextField amount = new JFXTextField();

    /**
     * Setzt grafik und wert
     * @author Robin Herder
     */
    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(amount);
            setAmount(item);
        }
    }

    /**
     * setzt progress und styling
     * @author Robin Herder
     */
    public void setAmount(Double value) {
        amount.setText(String.valueOf(value));
        amount.setMinSize(50, 30);
        amount.setPrefSize(50, 30);
        amount.setMaxSize(50, 30);
    }
}
