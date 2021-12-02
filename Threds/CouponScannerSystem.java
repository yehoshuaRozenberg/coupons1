package Threds;

import Beans.Coupon;
import DBDAO.CouponsDBDAO;
import DBDAO.DBDAO_Utils;
import SQL.ConnectionPool;
import SQL.NewDBUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Coupon Scanner System class
 * Thread To delete coupons whose end date has passed, once a day
 */
public class CouponScannerSystem implements Runnable {
    private static final int CHEK_TIME = 1000 * 60 * 60 * 24;
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * default c'tor
     */
    public CouponScannerSystem() {
    }

    /**
     * Every 24 hours deletes all coupons whose end date has passed
     */
    @Override
    public void run() {
        System.out.println("Starting thread");
        while (true) {
            couponsDBDAO.deleteIfDatePass();
            try {
                Thread.sleep(CHEK_TIME);
            } catch (InterruptedException e) {
                System.out.println("TasksThread has been stopped!");
            }
        }
    }
}

/*
 //add to coupons dbdao override
    final private static String GET_ALL_COUPONS = "SELECT * FROM `couponsDB`.`coupons`";

    @Override
    public void run() {
        System.out.println("Starting thread");
        while (true) {
            List<Coupon> coupons = null;
            try {
                coupons = DBDAO_Utils.buildCuponArrayFromResult(NewDBUtils.runSimpleQueryGetResult(GET_ALL_COUPONS));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for (Coupon item : coupons) {
                if (item.getEndDate().before(Date.valueOf(LocalDate.now()))) {
                    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
                    couponsDBDAO.deleteCoupon(item.getId());
                }
            }
            try {
                Thread.sleep(CHEK_TIME);
            } catch (InterruptedException interruptedException) {
                System.out.println("TasksThread has been stopped!");
            }
        }
    }
}

*/
