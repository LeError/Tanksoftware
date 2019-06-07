package gasStationSoftware.util;

import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.models.Item;
import javafx.scene.control.TableCell;

public class TextFielCustom
extends TableCell<Item, Float> {
    private JFXTextField amount = new JFXTextField();

    private float item = -1;

    /**
     * Setzt grafik und wert
     * @author Robin Herder
     */
    @Override protected void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(amount);
            if (this.item == -1) {
                setAmount(item);
            } else {
                setAmount(this.item);
            }
        }
    }

    /**
     * setzt größe und wert
     * @author Robin Herder
     */
    public void setAmount(Float value) {
        amount.setText(String.valueOf(value));
        amount.setMinSize(50, 30);
        amount.setPrefSize(50, 30);
        amount.setMaxSize(50, 30);
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                super.updateItem(Float.parseFloat(newValue), true);
                this.item = Float.parseFloat(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
