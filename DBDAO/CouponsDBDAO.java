package DBDAO;

import Beans.Category;
import Beans.Coupon;
import Interfaces.CouponsDAO;
import SQL.NewDBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Customer Data base DAO class
 */
public class CouponsDBDAO implements CouponsDAO {
    private static final String ADD_COUPON = "INSERT INTO `couponsDB`.`coupons` (`ID_COMPANY`,`iD_CATEGORY`,`title`,`DESCRIPTION`,`START_DATE`,`END_DATE`,`AMOUNT`,`PRICE`,`IMAGE`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `couponsDB`.`coupons` set `iD_CATEGORY`=?, `title`=?, `DESCRIPTION`=? ,`START_DATE`=? , `END_DATE`=? , `AMOUNT`=? , `PRICE`=? , `IMAGE`=? WHERE id=?";
    private static final String DELETE_COUPON = "DELETE FROM `couponsDB`.`coupons` where id=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM couponsdb.coupons;";
    private static final String GET_ALL_COUPONS_BY_COMPANY = "SELECT * FROM `couponsDB`.`coupons` WHERE id_company=?";
    private static final String GET_ALL_COUPONS_BY_CUSTOMER = "SELECT * FROM `couponsDB`.`coupons` INNER JOIN `couponsDB`.`customers_vs_coupons` ON `id` = `coupon_id` and `customer_id`=?";
    private static final String GET_ONE_COUPONS = "SELECT * FROM `couponsDB`.`coupons` WHERE id=?";
    private static final String REDUCE_AMOUNT = "update `couponsDB`.`coupons`set `amount`= amount -1 where `id`=?;";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `couponsDB`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (?,?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `couponsDB`.`customers_vs_coupons` where coupon_id=? and customer_id=?";
    private static final String GET_COUPONS_BY_COMPANY_AND_CATEGORY = "SELECT * FROM `couponsDB`.`coupons` WHERE ID_COMPANY=? and iD_CATEGORY=?";
    private static final String GET_COUPONS_BY_COMPANY_UNTIL_PRICE = "SELECT * FROM `couponsDB`.`coupons` WHERE ID_COMPANY=? and price<=?";
    private static final String GET_COUPONS_BY_CUSTOMER_AND_CATEGORY = "SELECT * FROM `couponsDB`.`coupons` INNER JOIN `couponsDB`.`customers_vs_coupons` ON `id` = `coupon_id` where `customer_id`=? and `id_category`=?";
    private static final String GET_COUPONS_BY_CUSTOMER_UNTIL_PRICE = "SELECT * FROM `couponsDB`.`coupons` INNER JOIN `couponsDB`.`customers_vs_coupons` ON `id` = `coupon_id` where `customer_id`=? and `price`<=?";
    private static final String IS_PURCHES_EXISTS = "select exists (select 1 from `couponsDB`.`customers_vs_coupons` where `coupon_id`=? and `customer_id`=?) limit 1";
    private static final String IS_AMOUNT_0 = "select `amount` from `couponsDB`.`coupons` where `id`=?";
    private static final String IS_DATE_PASSED = "select exists (select 1 from `couponsDB`.`coupons` where `id`=? and `end_date`>=sysdate() limit 1)";
    private static final String DELETE_BY_END_DATE = "delete FROM `couponsDB`.`coupons` WHERE id>0 and end_date<sysdate()";
    private static final String GET_ALL_COUPONS_PURCHASES = "SELECT * FROM couponsdb.customers_vs_coupons;";
    private static final String IS_COUPON_NAME_EXISTS_IN_COMPANY = "select exists (select 1 from `couponsDB`.`coupons` where `ID_COMPANY`=? and `title`=? limit 1)";

    /**
     * Gets a coupon and adds it to the coupons table in Sql
     *
     * @param coupon - the coupon we want to add
     */
    @Override
    public void addCoupon(Coupon coupon) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, coupon.getCompanyID());
        params.put(2, coupon.getCatagory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(ADD_COUPON, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
    }

    /**
     * Gets a coupon, and by the id coupon, update it in the coupons table in Sql
     *
     * @param coupon - id: the id of the coupon we want to update. Category, Title, Description, StartDate, EndDate, Amount, Price, Image - The data we update
     */
    @Override
    public void updateCoupon(Coupon coupon) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, coupon.getCatagory().ordinal() + 1);
        params.put(2, coupon.getTitle());
        params.put(3, coupon.getDescription());
        params.put(4, coupon.getStartDate());
        params.put(5, coupon.getEndDate());
        params.put(6, coupon.getAmount());
        params.put(7, coupon.getPrice());
        params.put(8, coupon.getImage());
        params.put(9, coupon.getId());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(UPDATE_COUPON, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
    }

    /**
     * delete coupon from the coupons table and customer vs coupon in sql, by the id coupon,
     *
     * @param id: the id of the coupon we want to delete
     */
    @Override
    public void deleteCoupon(int id) {
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareIntStatement(DELETE_COUPON, id));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
    }

    /**
     * Receives the data of all the Coupons Purchases from the customers_vs_coupons table in Sql, and creates String of Coupons Purchases  from it
     *
     * @return String of Coupons Purchases (customer and coupon of each purchase)
     */
    public String getAllCouponsPurchases() {
        String coupons = null;
        try {
            coupons = DBDAO_Utils.stringPurchases(NewDBUtils.runSimpleQueryGetResult(GET_ALL_COUPONS_PURCHASES));
        } catch (SQLException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return coupons;
    }

    /**
     * Receives the data of all the coupons that Belonging to a particular company, from the coupons table in Sql, according by company id. and creates an arrayLisst of coupons from it
     *
     * @param id - id Of the company we want to get all its coupons
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByCompany(int id) {
        ArrayList<Coupon> coupons = null;
        try {
            coupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareIntStatement(GET_ALL_COUPONS_BY_COMPANY, id)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return coupons;
    }

    /**
     * Receives the data of all the coupons from the  coupons table in Sql, and creates an arrayLisst of coupons from it
     *
     * @return arrayLisst of coupons
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = null;
        try {
            coupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runSimpleQueryGetResult(GET_ALL_COUPONS));
        } catch (SQLException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return coupons;
    }

    /**
     * Receives the data of one coupon (by id coupon) from the  coupons table in Sql, and creates coupon from it
     *
     * @param id: the id of the coupon we want to get
     * @return the coupon
     */
    @Override
    public Coupon getOneCoupon(int id) {
        Coupon coupon = null;
        try {
            ResultSet resultSet = NewDBUtils.runStatmentGetResult(NewDBUtils.prepareIntStatement(GET_ONE_COUPONS, id));
            while (resultSet.next()) {
                coupon = new Coupon(resultSet.getInt(2), Category.values()[(resultSet.getInt(3)) - 1], resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
                coupon.setId(resultSet.getInt(1));
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return coupon;
    }

    /**
     * Adds purchase coupon to customer vs coupon table in sql, according to customer id and coupon id.
     * In addition, reduces the amount of this coupon by 1
     *
     * @param customerId -the id of the customer that buy this coupon
     * @param couponId   -the id of The coupon we bought
     */
    @Override
    public void addCouponPurchase(int customerId, int couponId) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(ADD_COUPON_PURCHASE, params));
            NewDBUtils.runStatment(NewDBUtils.prepareIntStatement(REDUCE_AMOUNT, couponId));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
    }

    /**
     * Checks if a specific purchase has already occurred - that is, if a specific customer has already bought a specific coupon
     *
     * @param customerId -the id of the customer we check if buy this coupon
     * @param couponId   - the id of The coupon we check if we bought by this customer
     * @return boolean answer. pay attention!: if this purchase not exist return true, and if this purchase  exist return false
     */
    public boolean isPurchaseExists(int customerId, int couponId) {
        boolean res = true;
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, couponId);
        params.put(2, customerId);
        try {
            res = !DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareMapStatement(IS_PURCHES_EXISTS, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return res;
    }

    /**
     * Checks if a quantity remains in a particular coupon, or if its quantity is 0
     *
     * @param couponId - the id of the coupon we check
     * @return boolean answer: true if quantity is 0, false if More than 0
     */
    public boolean isAmount0(int couponId) {
        boolean res = false;
        try {
            if (DBDAO_Utils.intFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareIntStatement(IS_AMOUNT_0, couponId))) == 0) {
                res = true;
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return res;
    }

    /**
     * Checks if the expiration date of a specific coupon (according to coupon id) has passed
     *
     * @param couponId - the id of the coupon we check
     * @return boolean answer: true if date passed, false if not passed
     */
    public boolean isDatePass(int couponId) {
        boolean res = false;
        try {
            res = DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareIntStatement(IS_DATE_PASSED, couponId)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return res;
    }

    /**
     * Deletes all coupons whose date has passed
     */
    public void deleteIfDatePass() {
        try {
            NewDBUtils.runSimpleQuery(DELETE_BY_END_DATE);
        } catch (SQLException e) {
            System.out.println("Eror in SQL: " + e);
        }
    }

    /**
     * Deletes purchase of a coupon from a customer  vs coupon table in sql, according to customer id and coupon id
     *
     * @param customerId - the id of the customer we delete his purchase
     * @param couponId   -the id of the coupon we delete his purchase
     */
    @Override
    public void deleteCouponPurchase(int customerId, int couponId) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, couponId);
        params.put(2, customerId);
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(DELETE_COUPON_PURCHASE, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }

    }

    /**
     * Receives the data of all the coupons of one customer from Sql, and creates an arrayLisst of coupons from it
     *
     * @param id - the id of the customer
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByCustomer(int id) {
        ArrayList<Coupon> coupons = null;
        try {
            coupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareIntStatement(GET_ALL_COUPONS_BY_CUSTOMER, id)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return coupons;
    }

    /**
     * Receives the data of all the coupons Of one company, belonging to a specific category, from Sql. and creates an arrayLisst of coupons from it
     *
     * @param companyId - the id of the company
     * @param num       - the number of category in sql (in intelengi the number small in 1)
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByCompanyAndCategory(int companyId, int num) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, companyId);
        params.put(2, num);
        ArrayList<Coupon> cupons = null;
        try {
            cupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareMapStatement(GET_COUPONS_BY_COMPANY_AND_CATEGORY, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return cupons;
    }

    /**
     * Receives the data of all the coupons Of one company, until a specific price, from Sql. and creates an arrayLisst of coupons from it
     *
     * @param id    - the id of the company
     * @param price - the price
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByCompanyAndPrice(int id, double price) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, id);
        params.put(2, price);
        ArrayList<Coupon> cupons = null;
        try {
            cupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareMapStatement(GET_COUPONS_BY_COMPANY_UNTIL_PRICE, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return cupons;
    }

    /**
     * Receives the data of all the coupons Of one customer, belonging to a specific category, from Sql. and creates an arrayLisst of coupons from it
     *
     * @param id  - the id of the customer
     * @param num - the number of category in sql (in intelengi the number small in 1)
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByIdAndCategory(int id, int num) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, id);
        params.put(2, num);
        ArrayList<Coupon> cupons = null;
        try {
            cupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareMapStatement(GET_COUPONS_BY_CUSTOMER_AND_CATEGORY, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return cupons;
    }

    /**
     * Receives the data of all the coupons Of one customer, until a specific price, from Sql. and creates an arrayLisst of coupons from it
     *
     * @param id    - the id of the customer
     * @param price - the price
     * @return arrayLisst of coupons
     */
    public ArrayList<Coupon> getAllCouponsByIdAndPrice(int id, double price) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, id);
        params.put(2, price);
        ArrayList<Coupon> cupons = null;
        try {
            cupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runStatmentGetResult(
                    NewDBUtils.prepareMapStatement(GET_COUPONS_BY_CUSTOMER_UNTIL_PRICE, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return cupons;
    }

    /**
     * Checks if there is a coupon with the same name in a specific company
     *
     * @param id   - id fo the company ew check
     * @param name - the coupon's name we check
     * @return boolean answer: if there is a coupon with the same name true, and else - false
     */
    public boolean isCouponNameExistsInCompany(int id, String name) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, id);
        params.put(2, name);
        boolean res = true;
        try {
            res = DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareMapStatement(IS_COUPON_NAME_EXISTS_IN_COMPANY, params)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Eror in SQL: " + e);
        }
        return res;
    }

}
