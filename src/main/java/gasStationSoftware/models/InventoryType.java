package gasStationSoftware.models;

public enum InventoryType {

    Good("GOOD"), Fuel("FUEL");

    private final String TYPE;

    private InventoryType(String type) {
        TYPE = type;
    }

    public String getTYPE() {
        return TYPE;
    }

}
