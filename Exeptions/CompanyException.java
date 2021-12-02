package Exeptions;

/**
 * Company Exception class
 */
public class CompanyException extends Exception {

    /**
     * default c'tor
     * Announces Company exception
     */
    public CompanyException() {
        super("Company Exception: ");
    }

    /**
     * Receives a message and announces Company exception with the message
     *
     * @param message - The message he receives, and throws away in case of exception
     */
    public CompanyException(String message) {
        super(message);
    }
}
