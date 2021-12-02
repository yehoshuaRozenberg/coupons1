package Exeptions;

/**
 * Admin Exception class
 */
public class AdminException extends Exception {
    /**
     * default c'tor
     * Announces Administrator exception
     */
    public AdminException() {
        super("Admin Exception: ");
    }

    /**
     * Receives a message and announces Administrator exception with the message
     *
     * @param message - The message he receives, and throws away in case of exception
     */
    public AdminException(String message) {
        super(message);
    }
}
