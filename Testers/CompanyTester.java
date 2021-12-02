package Testers;

import Beans.Category;
import Beans.Coupon;
import DBDAO.CouponsDBDAO;
import Exeptions.CompanyException;
import Exeptions.UserException;
import Fasade.CompanyFasade;
import Fasade.LoginManager;
import Fasade.clientTipe;

import java.sql.Date;

public class CompanyTester {
    public CompanyTester() {
        System.out.println();
        System.out.println("**************************************************************");
        System.out.println();
        System.out.println("===============log in company (1)===================");
        System.out.println("======try to sign in with wrong email");
        CompanyFasade companyFasade = null;
        try {
            companyFasade = (CompanyFasade) LoginManager.getInstance().logIn("jblkjbu@dmnu", "586126333", clientTipe.Companies);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("======try to sign in with wrong password");
        try {
            companyFasade = (CompanyFasade) LoginManager.getInstance().logIn("john@brice", "1234567", clientTipe.Companies);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("=====sign in correctly");
        try {
            companyFasade = (CompanyFasade) LoginManager.getInstance().logIn("john@brice", "586126333", clientTipe.Companies);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("===================add new coupons====================");
        System.out.println("========add 3 coupons correctly. can see also that another company sells roladin and it works");
        try {
            if (companyFasade != null) {
                companyFasade.addCoupon(new Coupon(companyFasade.getId(), Category.VACATION, "jerusalem", "1+1", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 3, 10000, "IN COMPUTER"));
                companyFasade.addCoupon(new Coupon(companyFasade.getId(), Category.ELECTRICITY, "Mosquito killer", "-30%", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 1000, 30, "IN COMPUTER"));
                companyFasade.addCoupon(new Coupon(companyFasade.getId(), Category.RESTRAURANT, "roladin", "1+1", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 250, 100, "IN COMPUTER"));
            }
        } catch (CompanyException e) {
            System.out.println(e);
        }


        System.out.println();
        System.out.println("======add coupon that name exists in this company");
        try {
            companyFasade.addCoupon(new Coupon(companyFasade.getId(), Category.RESTRAURANT, "jerusalem", "-5%", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 10, 10000, "IN COMPUTER"));
        } catch (CompanyException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("===================update coupon. (can see that company id doesn't change)====================");
        Coupon coupon1 = companyFasade.getCompanyCoupons().get(1);
        try {
            System.out.println(coupon1);
            coupon1.setCompanyID(4);
            coupon1.setTitle("cellPhone");
            coupon1.setCatagory(Category.ELECTRICITY);
            coupon1.setDescription("-400$");
            coupon1.setStartDate(Date.valueOf("2021-3-3"));
            coupon1.setEndDate(Date.valueOf("2021-10-10"));
            coupon1.setAmount(25);
            coupon1.setPrice(500);
            coupon1.setImage("SAMSUNG");
            companyFasade.updateCoupon(coupon1);
            System.out.println(companyFasade.getCompanyCoupons().get(1));
        } catch (CompanyException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("===================bonus: try update coupon with name exist in company====================");
        try {
            coupon1.setCompanyID(4);
            coupon1.setTitle("cellPhone");
            coupon1.setCatagory(Category.ELECTRICITY);
            coupon1.setDescription("-500$");
            coupon1.setStartDate(Date.valueOf("2021-2-3"));
            coupon1.setEndDate(Date.valueOf("2021-11-10"));
            coupon1.setAmount(20);
            coupon1.setPrice(600);
            coupon1.setImage("SAMSUNG");
            companyFasade.updateCoupon(coupon1);
            // System.out.println(companyFasade.getAllCouponsOfCompany().get(1));
        } catch (CompanyException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("===================delete coupon. also in purchases====================");
        System.out.println("coupons of company before delete: \n" + companyFasade.getCompanyCoupons());
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        System.out.println("purchases before delete: \n" + couponsDBDAO.getAllCouponsPurchases());
        companyFasade.deleteCoupon(companyFasade.getCompanyCoupons().get(0).getId());
        System.out.println("coupons of company after delete: \n" + companyFasade.getCompanyCoupons());
        System.out.println("purchases after delete: \n" + couponsDBDAO.getAllCouponsPurchases());


        System.out.println();
        System.out.println("===================get all coupons by company====================");
        System.out.println(companyFasade.getCompanyCoupons());


        System.out.println();
        System.out.println("===================get all coupons by company and category====================");
        System.out.println(companyFasade.getCompanyCoupons(Category.ELECTRICITY));


        System.out.println();
        System.out.println("===================get all coupons by company until price====================");
        System.out.println(companyFasade.getCompanyCoupons(200));


        System.out.println();
        System.out.println("===================get company details====================");
        System.out.println(companyFasade.getCompanyDetails());


    }
}
