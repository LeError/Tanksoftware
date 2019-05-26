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
     * Constructor der Exeption
     * @param message
     * @author Robin Herder
     */
    public DataFileNotFoundException(String message) {
        super(message);
    }
}