package Testers;

import DBDAO.CouponsDBDAO;
import SQL.ConnectionPool;
import Threds.CouponScannerSystem;

import java.sql.SQLException;

public class ThreadTester {
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    public ThreadTester() {
        System.out.println("===================activate thread====================");
        System.out.println("===================before activate thread: ");
        System.out.println(couponsDBDAO.getAllCoupons());
        System.out.println(couponsDBDAO.getAllCouponsPurchases());


        Thread scannerSystem = new Thread(new CouponScannerSystem());

        scannerSystem.start();
        try {
            scannerSystem.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===================after activate thread: ");
        System.out.println(couponsDBDAO.getAllCoupons());
        System.out.println(couponsDBDAO.getAllCouponsPurchases());
        scannerSystem.interrupt();
        System.out.println();
        System.out.println("======================================");
        System.out.println("==========close all connections and system");
        try {
            scannerSystem.join(100);
        } catch (InterruptedException err) {
            System.out.println(err);
        }
        try {
            ConnectionPool.getInstance().closeAllConnection();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err);
        }
        System.exit(200);


    }
}
