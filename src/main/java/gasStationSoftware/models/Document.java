package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public abstract class Document {

    private final DocumentType DOC_TYPE;
    private final String NAME;
    private final Date DATE;

    /**
     * Constructor Document
     * @param docType
     * @param name
     * @param date
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
     * Gibt den Namen des Dokuments zurück
     * @return NAME
     * @author Robin Herder
     */
    public String getNAME() {
        return NAME;
    }

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
     *
     * @return
     * @author Robin Herder
     */
    public abstract String[] getLinesForFile();
}
