package gasStationSoftware.exceptions;

public class NumberOutOfRangeException
extends Exception {

    /**
     * Constructor der Exeption
     * @author Robin Herder
     */
    public NumberOutOfRangeException() {
        super();
    }

    /**
     * Constructor der Exeption mit Fehlertext
     * @param message fehlermeldung
     * @author Robin Herder
     */
    public NumberOutOfRangeException(String message) {
        super(message);
    }
}