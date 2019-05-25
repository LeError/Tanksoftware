package gasStationSoftware.models;

public enum DocumentType {

    receipt("receipt"),
    goodDelivery("good delivery"),
    goodOrder("good Order"),
    fuelDelivery("fuel delivery"),
    fuelOrder("fuel order");

    private final String TYPE;

    private DocumentType(String type) {
        TYPE = type;
    }

    public String getTYPE() {
        return TYPE;
    }

}
