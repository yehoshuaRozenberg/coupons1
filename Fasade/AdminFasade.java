package Fasade;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompanysDBDAO;
import DBDAO.CustomersDBDAO;
import Exeptions.AdminException;

import java.util.ArrayList;

/**
 * Admin Facade class
 * The methods of this class use methods from the CompanysDBDAO class and CustomersDBDAO class
 */
public class AdminFasade extends clientFasade {
    private final String EMAIL = "admin@admin.com";
    private final String PASSWORD = "admin";
    private CompanysDBDAO companysDBDAO = new CompanysDBDAO();
    private CustomersDBDAO customersDBDAO = new CustomersDBDAO();

    /**
     * Checks whether the email and password entered match the admin email and password stored in the system
     *
     * @param email    - the email was entered
     * @param password - the password was entered
     * @return boolean answer: if They are the same - true, else- false
     */
    public boolean logIn(String email, String password) {
        return email.equals(EMAIL) && password.equals(PASSWORD);
    }

    /**
     * Gets a company and adds it in the companies table in Sql
     *
     * @param company - the company we want to add
     * @throws AdminException if company name or email already exists
     */
    public void addCompany(Company company) throws AdminException {
        if (companysDBDAO.isCompanyMailOrNameExists(company.getName(), company.getEmail())) {
            throw new AdminException("company name or email already exists, try enter a new one");
        }
        companysDBDAO.addCompany(company);
        System.out.println("company successfully added!");

    }

    /**
     * Gets a company, and  update it in the companies table in Sql
     *
     * @param company - the company we update. id: the id of the company we want to update. email and password - The data we update
     * @throws AdminException if company email already exists or if try to change company name
     */
    public void updateCompany(Company company) throws AdminException {
        if (!company.getName().equals(companysDBDAO.getOneCompany(company.getId()).getName())) {
            throw new AdminException("can not change company name");
        }

        if (companysDBDAO.isCompanyMailOrNameExists("", company.getEmail())) {   // bonus, but its not the best way ,to write name=""..
            throw new AdminException("company email already exists");
        }
        companysDBDAO.updateCompany(company);
        System.out.println("company successfully updated!");
    }

    /**
     * delete company from the company table in sql, by the id company
     *
     * @param companyId - the id of the company we want to delete
     */
    public void deleteCompany(int companyId) {
        companysDBDAO.deleteCompany(companyId);
        System.out.println("company successfully deleted!");
    }

    /**
     * Receives the data of all the companies from the companies table in Sql, and creates an arrayLisst of companies from it
     *
     * @return rrayLisst of companies
     */
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = companysDBDAO.getAllCompanys();
        return companies;
    }

    /**
     * Receives the data of one company (by id company) from the  companies table in Sql, and creates company from it
     *
     * @param companyId - the id of the company we want to get
     * @return the company
     * @throws AdminException if company company don't exists
     */
    public Company getOneCompany(int companyId) throws AdminException {
        Company company = companysDBDAO.getOneCompany(companyId);
        if (company == null) {
            throw new AdminException("company dosn't exists, returning null: ");
        }
        return company;
    }

    /**
     * Gets a customer and adds it to the customer table in Sql
     *
     * @param customer -  the customer we want to add
     * @throws AdminException if the customer email already exists
     */
    public void addCustomer(Customer customer) throws AdminException {
        if (customersDBDAO.isCustomerMailExists(customer.getEmail())) {
            throw new AdminException("customer email already exists, try enter a new one");
        } else {
            customersDBDAO.addCustomer(customer);
            System.out.println("customer successfully added!");
        }
    }

    /**
     * Gets a customer, and by the id customer, update it in the customers table in Sql
     *
     * @param customer the customer we update. id- the id of the customer we want to update. FirstName, LastName, Email, Password - The data we update
     * @throws AdminException if the customer email we try update already exists
     */
    public void updateCustomer(Customer customer) throws AdminException {
        if (customersDBDAO.isCustomerMailExists(customer.getEmail())) {
            throw new AdminException("customer email already exists");
        }
        customersDBDAO.updateCustomer(customer);
        System.out.println("customer successfully updated!");
    }

    /**
     * delete Customer from the Customer table in sql, by the id Customer
     *
     * @param customerId the id of the Customer we want to delete
     */
    public void deleteCustomer(int customerId) {
        customersDBDAO.deleteCustomer(customerId);
        System.out.println("customer successfully deleted!");
    }

    /**
     * Receives the data of all the customers from the customers table in Sql, and creates an arrayLisst of customers from it
     *
     * @return arrayLisst of customers
     */
    public ArrayList<Customer> getAllCustomers() {
        return customersDBDAO.getAllCustomers();
    }

    /**
     * Receives the data of one customer (by id customer) from the customers table in Sql, and creates customer from it
     *
     * @param customerId the id of the customer we want to get
     * @return the customer
     * @throws AdminException if customer don't exists
     */
    public Customer getOneCustomer(int customerId) throws AdminException {
        Customer customer = customersDBDAO.getOneCustomers(customerId);
        if (customer == null) {
            throw new AdminException("customer dosn't exists, returning null: ");
        }
        return customer;
    }

}
