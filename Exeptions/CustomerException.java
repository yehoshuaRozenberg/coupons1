package Exeptions;

/**
 * Customer Exception class
 */
public class CustomerException extends Exception {

    /**
     * default c'tor
     * Announces Customer exception
     */
    public CustomerException() {
        super("Customer Exception: ");
    }

    /**
     * Receives a message and announces Customer exception with the message
     *
     * @param message - The message he receives, and throws away in case of exception
     */
    public CustomerException(String message) {
        super(message);
    }
}
