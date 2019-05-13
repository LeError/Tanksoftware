package gasStationSoftware.models;

public class ItemType {

    private final String LABEL;
    private final int INVENTORY_NUMBER;
    private final InventoryType TYPE;

    public ItemType(String label, int inventoryNumber, InventoryType iType) {
        LABEL = label;
        INVENTORY_NUMBER = inventoryNumber;
        TYPE = iType;
    }

    public String getLABEL() {
        return LABEL;
    }

    public int getINVENTORY_NUMBER() {
        return INVENTORY_NUMBER;
    }

    public InventoryType getTYPE() {
        return TYPE;
    }

    public String getTYPE_LABEL() {
        return TYPE.getTYPE();
    }
}
