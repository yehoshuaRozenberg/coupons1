package DBDAO;

import Beans.Company;
import Beans.Customer;
import Interfaces.CustomersDAO;
import SQL.ConnectionPool;
import SQL.NewDBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Customer Data base DAO class
 */
public class CustomersDBDAO implements CustomersDAO {
    private static final String ADD_CUSTOMER = "INSERT INTO `couponsDB`.`customers` (`first_name`,`last_name`,`email`,`password`) VALUES (?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `couponsDB`.`customers` SET `first_name`=? , `last_name`=? ,`email`=?,`password`=? WHERE id=?";
    private static final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM `couponsDB`.`customers` WHERE `id`= ?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponsDB`.`customers`";
    private static final String DELETE_BY_ID = "DELETE FROM `couponsDB`.`customers` where id=?";
    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM couponsDB.customers WHERE email =? LIMIT 1)";
    private static final String LOG_IN = "SELECT EXISTS (SELECT 1 FROM `couponsDB`.`customers` WHERE email =? and password=? limit 1) ;";
    private static final String GET_ID_BY_EMAIL = "SELECT id FROM `couponsDB`.`customers` WHERE email= ?";


    /**
     * Receives an email and password of a customer and checks in Sql if there is a customer with the same email and password
     *
     * @param email    - customer email
     * @param password - customer password
     * @return boolean - true if  exists, false if not exist
     */
    @Override
    public boolean isCustomerExists(String email, String password) {
        boolean res = true;
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, email);
        params.put(2, password);
        try {
            if (!DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareMapStatement(LOG_IN, params)))) {
                res = false;
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }
        return res;
    }

    /**
     * Receives an email of a customer and checks in Sql if there is a customer with the same email
     *
     * @param email - customer email
     * @return boolean - true if  exists email, false If not exist
     */
    public boolean isCustomerMailExists(String email) {
        boolean res = true;
        try {
            if (!DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareStringStatement(IS_EXISTS, email)))) {
                res = false;
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }
        return res;
    }

    /**
     * Gets a customer and adds it to the customer table in Sql
     *
     * @param customer - the customer we want to add
     */
    @Override
    public void addCustomer(Customer customer) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(ADD_CUSTOMER, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * Gets a customer, and by the id customer, update it in the customers table in Sql
     *
     * @param customer - id: the id of the customer we want to update. FirstName, LastName, Email, Password - The data we update
     */
    @Override
    public void updateCustomer(Customer customer) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customer.getId());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(UPDATE_CUSTOMER, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * delete Customer from the Customer table in sql, by the id Customer
     *
     * @param id: the id of the Customer we want to delete
     */
    @Override
    public void deleteCustomer(int id) {
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareIntStatement(DELETE_BY_ID, id));
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }

    }

    /**
     * Receives the data of all the customers from the  customers table in Sql, and creates an arrayLisst of customers from it
     *
     * @return arrayLisst of customers
     */
    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = NewDBUtils.runSimpleQueryGetResult(GET_ALL_CUSTOMERS);
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                customer.setId(resultSet.getInt(1));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return customers;
    }

    /**
     * Receives the data of one customer (by id customer) from the  customers table in Sql, and creates customer from it
     *
     * @param id: the id of the customer we want to get
     * @return the customer
     */
    @Override
    public Customer getOneCustomers(int id) {
        Customer customer = null;
        ResultSet resultSet;
        try {
            resultSet = NewDBUtils.runStatmentGetResult(NewDBUtils.prepareIntStatement(GET_ONE_CUSTOMER_BY_ID, id));
            while (resultSet.next()) {
                customer = new Customer(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                customer.setId(resultSet.getInt(1));
            }
        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    /**
     * Receives an id of customer from the customers table, according to the customer email
     *
     * @param email - the customer email
     * @return id of this customer
     */
    public static int getIdByEmail(String email) {
        int id = 0;
        try {
            id = DBDAO_Utils.intFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareStringStatement(GET_ID_BY_EMAIL, email)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }
        return id;
    }

}

