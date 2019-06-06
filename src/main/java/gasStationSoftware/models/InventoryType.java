package gasStationSoftware.models;

public enum InventoryType {

    Good("GOOD"), Fuel("FUEL");

    private final String TYPE;

    /**
     * Constructor InventoryType
     * @param type type des items
     * @author Robin Herder
     */
    private InventoryType(String type) {
        TYPE = type;
    }

    /**
     * Gibt den Type zurück
     * @return
     * @author Robin Herder
     */
    public String getTYPE() {
        return TYPE;
    }

}
