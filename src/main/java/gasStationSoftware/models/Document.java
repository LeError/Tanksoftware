package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.ArrayList;
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
     * @author Lea Buchhold
     */
    public Document(DocumentType docType, String name, Date date) {
        DOC_TYPE = docType;
        NAME = name;
        DATE = date;
    }

    /**
     * Gibt den Dokumententyp zurück
     * @return DOC_TYPE
     * @author Lea Buchhold
     */
    public DocumentType getDOC_TYPE() {
        return DOC_TYPE;
    }

    /**
     * DocType formatiert für tabelle
     * @return
     * @author Lea Buchhold
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
     * @author Lea Buchhold
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * Gibt das Datum zurück
     * @return date
     * @author Lea Buchhold
     */
    public String getDATE() {
        return Utility.getDateFormatted(DATE);
    }

    /**
     * Gibt das Erstelldatum des Dokuments zurück
     * @return DATE
     * @author Lea Buchhold
     */
    public Date getODATE() {
        return DATE;
    }

    /**
     * Zeilen für datei returnen
     * @author Lea Buchhold
     */
    public abstract ArrayList<String> getLinesForFile();

    /**
     * Total für tabelle formatieren
     * @author Lea Buchhold
     */
    public abstract String getTotalForTab();
}
