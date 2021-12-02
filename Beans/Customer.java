package Beans;

import DBDAO.CouponsDBDAO;

import java.util.ArrayList;

/**
 * customer class
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * c'tor get:
     *
     * @param firstName of customer
     * @param lastName  of customer
     * @param email     of customer
     * @param password  of customer
     *                  build instance of customer:
     *                  (id will set by SQL
     *                  coupon array will fill in when customer purchase coupon)
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * @return customer id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id receive id and set customer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return customer First Name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName receive first Name and set customer first Name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return customer Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName receive last Name and set customer last Name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return customer Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email receive email and set customer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return customer Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password receive password and set customer password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return array of customer's coupons
     */
    public ArrayList<Coupon> getCoupons() {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        this.coupons = couponsDBDAO.getAllCouponsByCustomer(this.id);
        return coupons;
    }

    /**
     * @param coupons receive array of customer's coupons and set customer's coupons
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * @return all values company by String
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", \n coupons=" + getCoupons() +
                '}';
    }
}
