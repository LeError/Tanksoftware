package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public abstract class Document {

    private final DocumentType DOC_TYPE;
    private final String NAME;
    private final Date DATE;

    /**
     * Constructor Document
     * @param docType Documenttype
     * @param name name des dokuments
     * @param date erstellungsdatum des dokuments
     * @author Robin Herder
     */
    public Document(DocumentType docType, String name, Date date) {
        DOC_TYPE = docType;
        NAME = name;
        DATE = date;
    }

    /**
     * Gibt den Dokumententyp zurück
     * @return DOC_TYPE
     * @author Robin Herder
     */
    public DocumentType getDOC_TYPE() {
        return DOC_TYPE;
    }

    /**
     * DocType formatiert für tabelle
     * @return
     * @author Robin Herder
     */
    public String getDOC_TYPEForTab() {
        String type = "";
        if (DOC_TYPE == DocumentType.fuelDelivery || DOC_TYPE == DocumentType.goodDelivery) {
            type = "Ausgabe";
        } else if (DOC_TYPE == DocumentType.receipt) {
            type = "Einnahme";
        }
        return type;
    }

    /**
     * Gibt den Namen des Dokuments zurück
     * @return NAME
     * @author Robin Herder
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * Gibt das Datum zurück
     * @return date
     * @author Robin Herder
     */
    public String getDATE() {
        return Utility.getDateFormatted(DATE);
    }

    /**
     * Gibt das Erstelldatum des Dokuments zurück
     * @return DATE
     * @author Robin Herder
     */
    public Date getODATE() {
        return DATE;
    }

    /**
     * Zeilen für datei returnen
     * @author Robin Herder
     */
    public abstract String[] getLinesForFile();

    /**
     * Total für tabelle formatieren
     * @author Robin Herder
     */
    public abstract String getTotalForTab();
}
