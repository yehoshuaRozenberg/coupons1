package DBDAO;

import Beans.Category;
import Beans.Coupon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data base DAO utils class
 * Contains methods for retrieving data from data base
 */
public class DBDAO_Utils {
    /**
     * Creates a list of coupons from a ResultSet (coming from the database)
     *
     * @param resultSet - ResultSet of data we received as an answer from sql
     * @return ArrayList of coupons
     * @throws SQLException if error in SQL
     */
    public static ArrayList<Coupon> buildCuponArrayFromResult(ResultSet resultSet) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        while (resultSet.next()) {
            Coupon coupon = new Coupon(resultSet.getInt(2), Category.values()[(resultSet.getInt(3)) - 1], resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
            coupon.setId(resultSet.getInt(1));
            coupons.add(coupon);
        }
        return coupons;
    }

    /**
     * @param resultSet - data (integer) we received as an answer from sql
     * @return boolean answer - Does the data we were looking for exist or not
     * @throws SQLException if error in SQL
     */
    public static boolean booleanFromResult(ResultSet resultSet) throws SQLException {
        boolean res = false;
        ResultSet resultSet1 = resultSet;
        while (resultSet1.next()) {
            if (resultSet.getInt(1) != 0) {
                res = true;
            }
        }
        return res;
    }

    /**
     * @param resultSet - data (integer) we received as an answer from sql
     * @return the integer we were looking for
     * @throws SQLException if error in SQL
     */
    public static int intFromResult(ResultSet resultSet) throws SQLException {
        int res = 0;
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        return res;
    }

    /**
     * Creates a list of coupons Purchases from a ResultSet (coming from the database)
     *
     * @param resultSet - ResultSet of data we received as an answer from sql
     * @return String of Purchases (customer and coupon of each purchase)
     * @throws SQLException if error in SQL
     */
    public static String stringPurchases(ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
            sb.append("customer: " + resultSet.getInt(1) + " - coupon: " + resultSet.getInt(2) + "\n");
        }
        return sb.toString();
    }

}
