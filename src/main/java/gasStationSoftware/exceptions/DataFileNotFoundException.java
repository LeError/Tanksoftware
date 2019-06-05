package gasStationSoftware.exceptions;

public class DataFileNotFoundException
extends Exception {

    /**
     * Constructor der Exeption
     * @author Robin Herder
     */
    public DataFileNotFoundException() {
        super();
    }

    /**
     * Constructor der Exeption mit fehlertext
     * @param message felhlermeldung
     * @author Robin Herder
     */
    public DataFileNotFoundException(String message) {
        super(message);
    }
}