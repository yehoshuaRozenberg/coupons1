package Interfaces;

import Beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {
    boolean isCustomerExists(String email, String password);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);

    ArrayList<Customer> getAllCustomers();

    Customer getOneCustomers(int id);
}
