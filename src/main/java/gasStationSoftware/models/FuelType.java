package gasStationSoftware.models;

public class FuelType {

    private final String LABEL;
    private final int INVENTORY_NUMBER;
    private final InventoryType TYPE;

    public FuelType(String label, int inventoryNumber) {
        LABEL = label;
        INVENTORY_NUMBER = inventoryNumber;
        TYPE = InventoryType.Fuel;
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
}
