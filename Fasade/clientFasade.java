package Fasade;

import DBDAO.CompanysDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Interfaces.CompanysDAO;
import Interfaces.CouponsDAO;
import Interfaces.CustomersDAO;

/**
 * client Fasade class (abstract)
 */
public abstract class clientFasade {
    protected CompanysDAO companysDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    /**
     * Checks whether the email and password entered match the email and password stored in the system (abstract)
     * @param email the email was entered
     * @param password the password was entered
     * @return boolean answer: if They are the same - true, else- false
     */
    public abstract boolean logIn(String email, String password);




}
