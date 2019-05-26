package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public abstract class Document {

    private final DocumentType DOC_TYPE;
    private final String NAME;
    private final Date DATE;

    public Document(DocumentType docType, String name, Date date) {
        DOC_TYPE = docType;
        NAME = name;
        DATE = date;
    }

    public DocumentType getDOC_TYPE() {
        return DOC_TYPE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getDATE() {
        return Utility.getDateFormatted(DATE);
    }

    public abstract String[] getLinesForFile();
}
