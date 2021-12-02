package Fasade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Exeptions.CustomerException;


import java.util.ArrayList;

/**
 * Customer Facade class
 * The methods of this class use methods from the CustomersDBDAO class and CouponsDBDAO class
 */
public class CustomerFasade extends clientFasade {
    private int id;
    private CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * default c'tor
     * id Updated when logging in
     */
    public CustomerFasade() {
    }

    /**
     * Checks whether the email and password entered is exist in customers table in sql
     *
     * @param email    - the email was entered
     * @param password - the password was entered
     * @return boolean answer: if is exist - update this.id and return true, if is not exist- false
     */
    public boolean logIn(String email, String password) {
        if (customersDBDAO.isCustomerExists(email, password)) {
            this.id = customersDBDAO.getIdByEmail(email);
            return true;
        } else return false;
    }

    /**
     * Get a coupon, and Adds purchase coupon to customer vs coupon table in sql, according to the coupon id and this customer id (the customer that do log in).
     * In addition, reduces the amount of this coupon by 1
     *
     * @param coupon - the The coupon we bought.
     * @throws CustomerException And describes the problem if: this purchase already exists, or if amount of coupon is zero, or if coupon end date passed
     */
    public void purchaseCoupon(Coupon coupon) throws CustomerException {
        if (!couponsDBDAO.isPurchaseExists(this.id, coupon.getId())) {
            throw new CustomerException("purchase exists, cant buy same coupon twice");
        }
        if (couponsDBDAO.isAmount0(coupon.getId())) {
            throw new CustomerException("amount is zero, cant complete purchase");
        }
        if (!couponsDBDAO.isDatePass(coupon.getId())) {
            throw new CustomerException("coupon end date passed, cant complete purchase");
        }
        couponsDBDAO.addCouponPurchase(this.id, coupon.getId());
        System.out.println("purchase complited");
    }

    /**
     * Receives the data of all the coupons that this customer purchased (the customer that do log in) from Sql, and creates an arrayLisst of coupons from it
     *
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getCustomerCoupons() {
        return couponsDBDAO.getAllCouponsByCustomer(this.id);
    }

    /**
     * Receives the data of all the coupons that this customer purchased (the customer that do log in), belonging to a specific category, from Sql. and creates an arrayLisst of coupons from it
     *
     * @param category - the category we want to get the coupons that belong to her
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        return couponsDBDAO.getAllCouponsByIdAndCategory(this.id, category.ordinal() + 1);
    }

    /**
     * Receives the data of all coupons of this customer (the customer that do log in) until a specific price, from Sql, and creates an arrayLisst of coupons from it
     *
     * @param maxPrice - the price
     * @return ArrayLisst of coupons
     */
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
        return couponsDBDAO.getAllCouponsByIdAndPrice(this.id, maxPrice);
    }

    /**
     * Receives the data of this customer (the customer that do log in) from the customers table in Sql, and creates customer from it
     *
     * @return the customer
     */
    public Customer getCustomerDetails() {
        return customersDBDAO.getOneCustomers(id);
    }


}
