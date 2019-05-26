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
     * Constructor der Exeption
     * @param message
     * @author Robin Herder
     */
    public NumberOutOfRangeException(String message) {
        super(message);
    }
}