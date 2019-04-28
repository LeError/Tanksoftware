package gasStationSoftware.models;

import java.awt.*;
import java.util.ArrayList;

public class StorageUnit {

    private String label;
    private final Point LOCATION;
    private ArrayList<Item> items = new ArrayList<>();

    public StorageUnit(String label, int x, int y) {
        this.label = label;
        LOCATION = new Point(x, y);
    }

    public String getLabel() {
        return label;
    }

    public Point getLOCATION() {
        return LOCATION;
    }

    public void addItem(Item Item) {
        items.add(Item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
