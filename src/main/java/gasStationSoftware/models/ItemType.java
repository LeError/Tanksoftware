package gasStationSoftware.models;

public class ItemType {

    private final String LABEL;
    private final int INVENTORY_NUMBER;
    private final InventoryType TYPE;

    /**
     * Constructor ItemType
     * @param label
     * @param inventoryNumber
     * @param iType
     * @author Robin Herder
     */
    public ItemType(String label, int inventoryNumber, InventoryType iType) {
        LABEL = label;
        INVENTORY_NUMBER = inventoryNumber;
        TYPE = iType;
    }

    /**
     * Gibt das Label zurück
     * @return LABEL
     * @author Robin Herder
     */
    public String getLABEL() {
        return LABEL;
    }

    /**
     * Gibt die Inventarnummer zurück
     * @return INVENTORY_NUMBER
     * @author Robin Herder
     */
    public int getINVENTORY_NUMBER() {
        return INVENTORY_NUMBER;
    }

    /**
     *
     * @return TYPE
     * @author Robin Herder
     */
    public InventoryType getTYPE() {
        return TYPE;
    }

    /**
     *
     * @return TYPE_LABEL
     * @author Robin Herder
     */
    public String getTYPE_LABEL() {
        return TYPE.getTYPE();
    }
}
