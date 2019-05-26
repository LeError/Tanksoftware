package gasStationSoftware.models;

import java.awt.*;
import java.util.ArrayList;

public class StorageUnit {

    private String label;
    private Point location;
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Constructor StorageUnit
     * @param label
     * @param x
     * @param y
     * @author Robin Herder
     */
    public StorageUnit(String label, int x, int y) {
        this.label = label;
        location = new Point(x, y);
    }

    /**
     *
     * @return label
     * @author Robin Herder
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gibt den location des Lagers zurück
     * @return location
     * @author Robin Herder
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Gibt den X-Wert zurück
     * @return x
     * @author Robin Herder
     */
    public int getX() {
        return location.x;
    }

    /**
     * Gibt den Y-Wert zurück
     * @return y
     * @author Robin Herder
     */
    public int getY() {
        return location.y;
    }

    /**
     * Fügt ein Item dem Lager hinzu
     * @param Item
     * @author Robin Herder
     */
    public void addItem(Item Item) {
        items.add(Item);
    }

    /**
     * Entfernt dem Lager das eingegebende Item
     * @param item
     * @author Robin Herder
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gibt alle Items des Lagers zurück
     * @return items
     * @author Robin Herder
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}
