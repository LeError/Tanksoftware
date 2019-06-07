package gasStationSoftware.models;

public class ItemType {

    private String label;
    private final int INVENTORY_NUMBER;
    private final InventoryType TYPE;

    /**
     * Constructor ItemType
     * @param label Bezeichnung des Items
     * @param inventoryNumber Invnummer des Items
     * @param iType abgrenzung fuel / good
     * @author Lea Buchhold
     */
    public ItemType(String label, int inventoryNumber, InventoryType iType) {
        this.label = label;
        INVENTORY_NUMBER = inventoryNumber;
        TYPE = iType;
    }

    /**
     * Gibt das Label zurück
     * @return label
     * @author Lea Buchhold
     */
    public String getLABEL() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gibt die Inventarnummer zurück
     * @return INVENTORY_NUMBER
     * @author Lea Buchhold
     */
    public int getINVENTORY_NUMBER() {
        return INVENTORY_NUMBER;
    }

    /**
     * gibt den InventoryType zurück good / fuel
     * @return TYPE
     * @author Lea Buchhold
     */
    public InventoryType getTYPE() {
        return TYPE;
    }

    /**
     * gib die InventoryType bezeichnung zurück
     * @return TYPE_LABEL
     * @author Lea Buchhold
     */
    public String getTYPE_LABEL() {
        return TYPE.getTYPE();
    }
}
