package Fasade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDAO.CompanysDBDAO;
import DBDAO.CouponsDBDAO;
import Exeptions.CompanyException;


import java.util.ArrayList;

/**
 * Company Facade class
 * The methods of this class use methods from the CompanysDBDAO class and CouponsDBDAO class
 */
public class CompanyFasade extends clientFasade {
    private int id;
    private CompanysDBDAO companysDBDAO = new CompanysDBDAO();
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * default c'tor
     * id Updated when logging in
     */
    public CompanyFasade() {
    }

    /**
     * Checks whether the email and password entered is exist in company table in sql
     *
     * @param email    - the email was entered
     * @param password - the password was entered
     * @return boolean answer: if is exist - update this.id and return true, if is not exist- false
     */
    public boolean logIn(String email, String password) {
        if (companysDBDAO.isCompanyExists(email, password)) {
            this.id = companysDBDAO.getIdByEmail(email);
            return true;
        } else return false;
    }

    /**
     * Gets a Coupon and adds it in the Coupon table in Sql
     *
     * @param coupon - the Coupon we want to add
     * @throws CompanyException if coupons name already exists in this company
     */
    public void addCoupon(Coupon coupon) throws CompanyException {
        if (couponsDBDAO.isCouponNameExistsInCompany(this.id, coupon.getTitle())) {
            throw new CompanyException("coupons name already exists in company");
        }
        couponsDBDAO.addCoupon(coupon);
        System.out.println("coupon successfully added");
    }

    /**
     * Gets a Coupon, and update it in the Coupons table in Sql
     *
     * @param coupon - the Coupon we want to update. id: the id of the coupon we want to update. Category, Title, Description, StartDate, EndDate, Amount, Price, Image - The data we update
     * @throws CompanyException if Coupon email already exists in this company
     */
    public void updateCoupon(Coupon coupon) throws CompanyException {
        if (couponsDBDAO.isCouponNameExistsInCompany(this.id, coupon.getTitle())) {
            throw new CompanyException("coupons name already exists in company");
        }
        couponsDBDAO.updateCoupon(coupon);
        System.out.println("coupon successfully updated");
    }

    /**
     * delete Coupon from the Coupons table in sql, by the Coupon id
     *
     * @param id - the id of the Coupon we want to delete
     */
    public void deleteCoupon(int id) {
        couponsDBDAO.deleteCoupon(id);
        System.out.println("coupon successfully deleted");
    }

    /**
     * Receives the data of all coupons of this company (the company that do log in) from the coupons table in Sql, and creates an arrayLisst of coupons from it
     *
     * @return ArrayLisst of coupons
     */
    public ArrayList<Coupon> getCompanyCoupons() {
        return companysDBDAO.getOneCompany(this.id).getCoupons();
    }

    /**
     * Receives the data of all coupons of this company (the company that do log in) that Belonging to a specific category from the coupons table in Sql, and creates an arrayLisst of coupons from it
     *
     * @param category the category we want to get the coupons that belong to her
     * @return ArrayLisst of coupons
     */
    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return couponsDBDAO.getAllCouponsByCompanyAndCategory(this.id, category.ordinal() + 1);
    }

    /**
     * Receives the data of all coupons of this company (the company that do log in) until a specific price, from the coupons table in Sql, and creates an arrayLisst of coupons from it
     *
     * @param maxPrice - the price
     * @return ArrayLisst of coupons
     */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        return couponsDBDAO.getAllCouponsByCompanyAndPrice(this.id, maxPrice);
    }

    /**
     * Receives the data of this company (the company that do log in) from the companies table in Sql, and creates company from it
     *
     * @return the company
     */
    public Company getCompanyDetails() {
        return companysDBDAO.getOneCompany(this.id);
    }

    /**
     * @return this id - the id of the company that do log in
     */
    public int getId() {
        return id;
    }


}
