package Fasade;

import Exeptions.UserException;

/**
 * Login Manager class
 */
public class LoginManager {
    /**
     * Login Manager instance
     */
    private static LoginManager instance = null;


    /**
     * Constructor for creating instance of class (SingleTon)
     */
    private LoginManager() {
    }

    /**
     * get instance of LoginManager (SingleTon)
     *
     * @return instance of class
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * Receives email password and customer type, and checks whether the data matches the data stored in the system
     *
     * @param email      - The email the client is trying to connect with
     * @param password   - The password the client is trying to connect with
     * @param clientTipe - The clientTipe the client is trying to connect with
     * @return Returns the relevant facade according to the type of client, provided the data is appropriate, otherwise reports an error in the data
     * @throws UserException If there is an error in the data entered
     */
    public clientFasade logIn(String email, String password, clientTipe clientTipe) throws UserException {
        switch (clientTipe) {
            case Administrator:
                AdminFasade adminFasade = new AdminFasade();
                if (adminFasade.logIn(email, password)) {
                    System.out.println("WELCOME ADMIN!");
                    return adminFasade;
                } else {
                    throw new UserException("wrong email or password");
                }
            case Companies:
                CompanyFasade companyFasade = new CompanyFasade();
                if (companyFasade.logIn(email, password)) {
                    System.out.println("WELCOME COMPANY!");
                    return companyFasade;
                } else {
                    throw new UserException("wrong email or password");
                }
            case Customers:
                CustomerFasade customerFasade = new CustomerFasade();
                if (customerFasade.logIn(email, password)) {
                    System.out.println("WELCOME CUSTOMER!");
                    return customerFasade;
                } else {
                    throw new UserException("wrong email or password");
                }
            default:
                throw new UserException("wrong details");

        }

    }
}
