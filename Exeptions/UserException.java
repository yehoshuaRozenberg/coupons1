package Exeptions;

/**
 * User Exception class
 */
public class UserException extends Exception {
    /**
     * default c'tor
     * Announces user exception
     */
    public UserException() {
    }

    /**
     * Receives a message and announces user exception with the message
     *
     * @param message - The message he receives, and throws away in case of exception
     */
    public UserException(String message) {
        super(message);
    }
}
