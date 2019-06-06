package gasStationSoftware.models;

public enum DocumentType {

    receipt("receipt"),
    goodDelivery("good delivery"),
    goodOrder("good Order"),
    fuelDelivery("fuel delivery"),
    fuelOrder("fuel order");

    private final String TYPE;

    /**
     * Constructor DocumentType
     * @param type type des dokuments
     * @author Robin Herder
     */
    private DocumentType(String type) {
        TYPE = type;
    }

    /**
     * Gibt den Type zurück
     * @return TYPE
     * @author Robin Herder
     */
    public String getTYPE() {
        return TYPE;
    }

}
