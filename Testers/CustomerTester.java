package Testers;

import Beans.Category;
import Beans.Coupon;
import DBDAO.CouponsDBDAO;
import Exeptions.CustomerException;
import Exeptions.UserException;
import Fasade.CustomerFasade;
import Fasade.LoginManager;
import Fasade.clientTipe;

import java.sql.Date;

public class CustomerTester {
    public CustomerTester() {
        System.out.println();
        System.out.println("**************************************************************");
        System.out.println();
        System.out.println("===============log in customer (2)===================");
        System.out.println("======try to sign in with wrong email");
        CustomerFasade customerFasade = new CustomerFasade();
        try {
            customerFasade = (CustomerFasade) LoginManager.getInstance().logIn("jblkjbu@dmnu", "987654", clientTipe.Customers);
        } catch (UserException err) {
            System.out.println(err);
        }

        System.out.println();
        System.out.println("======try to sign in with wrong password");
        try {
            customerFasade = (CustomerFasade) LoginManager.getInstance().logIn("david@ori", "1234567", clientTipe.Customers);
        } catch (UserException err) {
            System.out.println(err);
        }

        System.out.println();
        System.out.println("=====sign in correctly");
        try {
            customerFasade = (CustomerFasade) LoginManager.getInstance().logIn("david@ori", "987654", clientTipe.Customers);
        } catch (UserException err) {
            System.out.println(err);
        }

        System.out.println();
        System.out.println("===================add new coupon purchase====================");
        System.out.println("========purchase 3 coupons correctly");
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        try {
            customerFasade.purchaseCoupon(couponsDBDAO.getAllCoupons().get(1));
            customerFasade.purchaseCoupon(couponsDBDAO.getAllCoupons().get(2));
            customerFasade.purchaseCoupon(couponsDBDAO.getAllCoupons().get(3));
        } catch (CustomerException err) {
            System.out.println(err);
        }
        //show that amount reduced by 1. coupon  for example
        System.out.println(couponsDBDAO.getAllCoupons().get(1));

        System.out.println();
        System.out.println("========try purchase same coupon");
        try {
            customerFasade.purchaseCoupon(couponsDBDAO.getAllCoupons().get(1));
        } catch (CustomerException err) {
            System.out.println(err);
        }

        System.out.println();
        System.out.println("========try purchase coupon that amount 0");
        Coupon coupon1 = couponsDBDAO.getOneCoupon(3);
        coupon1.setAmount(0);
        couponsDBDAO.updateCoupon(coupon1);
        try {
            customerFasade.purchaseCoupon(couponsDBDAO.getOneCoupon(3));
        } catch (CustomerException err) {
            System.out.println(err);
        }

        System.out.println();
        System.out.println("========try purchase coupon that date passed");
        coupon1.setAmount(24);
        coupon1.setEndDate(Date.valueOf("2021-1-6"));
        couponsDBDAO.updateCoupon(coupon1);
        try {
            customerFasade.purchaseCoupon(couponsDBDAO.getOneCoupon(3));
        } catch (CustomerException e) {
            System.out.println(e);
        }

        System.out.println(couponsDBDAO.getAllCouponsPurchases());


        System.out.println();
        System.out.println("===================get all coupons of customer====================");
        System.out.println(customerFasade.getCustomerCoupons());


        System.out.println();
        System.out.println("===================get all coupons of customer by category====================");
        System.out.println(customerFasade.getCustomerCoupons(Category.ELECTRICITY));


        System.out.println();
        System.out.println("===================get all coupons of customer until price====================");
        System.out.println(customerFasade.getCustomerCoupons(100));


        System.out.println();
        System.out.println("===================get customers' details====================");
        System.out.println(customerFasade.getCustomerDetails());


        System.out.println();
    }
}
