package gasStationSoftware.models;

public enum InventoryType {

    Good("GOOD"), Fuel("FUEL");

    private final String TYPE;

    /**
     * Constructor InventoryType
     * @param type type des items
     * @author Lea Buchhold
     */
    InventoryType(String type) {
        TYPE = type;
    }

    /**
     * Gibt den Type zurück
     * @return
     * @author Lea Buchhold
     */
    public String getTYPE() {
        return TYPE;
    }

}
