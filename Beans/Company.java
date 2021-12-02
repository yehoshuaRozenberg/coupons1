package Beans;

import DBDAO.CouponsDBDAO;

import java.util.ArrayList;

/**
 * company class
 */
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * c'tor get:
     *
     * @param name     of company
     * @param email    of company
     * @param password of company
     *                 build instance of company:
     *                 (id will set by SQL
     *                 coupon array will fill in when company create coupon)
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * @return company id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id receive id and set company id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return company name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name receive name and set company name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return company email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email receive email and set company email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return company password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password receive password and set company password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return array of company's coupons
     */
    public ArrayList<Coupon> getCoupons() {
        this.coupons = couponsDBDAO.getAllCouponsByCompany(this.id);
        return coupons;
    }

    /**
     * @param coupons receive array of company's coupons and set company's coupons
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * @return all values company by String
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", \n coupons=" + getCoupons() +
                '}';
    }
}
