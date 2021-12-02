package Testers;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponsDBDAO;
import Exeptions.UserException;
import Fasade.AdminFasade;
import Fasade.LoginManager;
import Fasade.clientTipe;
import Exeptions.AdminException;
import SQL.NewDBUtils;

import java.sql.Date;
import java.sql.SQLException;

public class AdminTester {
    public AdminTester() {
        System.out.println("===============log in admin===================");
        System.out.println("======try to sign in with wrong email");
        AdminFasade adminFasade = null;
        try {
            adminFasade = (AdminFasade) LoginManager.getInstance().logIn("jj@admin.com", "admin", clientTipe.Administrator);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("======try to sign in with wrong password");
        try {
            AdminFasade adminFasade2 = (AdminFasade) LoginManager.getInstance().logIn("admin@admin.com", "ad", clientTipe.Administrator);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("=====sign in correctly");
        try {
            adminFasade = (AdminFasade) LoginManager.getInstance().logIn("admin@admin.com", "admin", clientTipe.Administrator);
        } catch (UserException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("==================add new company==================");
        System.out.println("=====add 3 new company correctly, can see also that even if password exists is ok");
        try {
            adminFasade.addCompany(new Company("superMarket", "super@super", "123456"));
            adminFasade.addCompany(new Company("Market", "market@super", "78hlhbl10"));
            adminFasade.addCompany(new Company("lising", "lising@gmail.com", "123456"));
        } catch (AdminException | NullPointerException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("=====add new company that name exists");
        try {
            adminFasade.addCompany(new Company("lising", "hhhh@dddd.com", "17852416"));
        } catch (AdminException | NullPointerException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("=====add new company that email exists");
        try {
            adminFasade.addCompany(new Company("rami levi", "super@super", "888888"));
        } catch (AdminException | NullPointerException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("===================update company (company number 1) ==================");
        System.out.println("============update email and password ");
        Company company1 = null;
        try {
            company1 = adminFasade.getOneCompany(1);
            company1.setEmail("john@brice");
            company1.setPassword("586126333");
            adminFasade.updateCompany(company1);
            System.out.println(adminFasade.getOneCompany(1));
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("============try to update name");
        try {
            company1.setName("johnBrice");
            adminFasade.updateCompany(company1);
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("============bonus- try to update email that exists");
        try {
            company1 = adminFasade.getOneCompany(2);
            company1.setEmail("lising@gmail.com");
            adminFasade.updateCompany(company1);
        } catch (AdminException e) {
            System.out.println(e);
        }

        hardCodeCouponsInsertInto();

        System.out.println();
        System.out.println("============get one company (num 3) by id ============");
        Company company;
        try {
            company = adminFasade.getOneCompany(3);
            System.out.println(company);
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("==================get all companies=================");
        adminFasade.getAllCompanies().forEach(System.out::println);


        System.out.println();
        System.out.println("======================add new 3 customers==========================");
        System.out.println("=====add 3 new customers correctly");
        try {
            adminFasade.addCustomer(new Customer("yossef", "yzhak", "yossef@yzhak", "123456"));
            adminFasade.addCustomer(new Customer("david", "ori", "david@ori", "987654"));
            adminFasade.addCustomer(new Customer("yehushua", "rozenberg", "yehushua@rozenberg", "9876"));
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("=====add customer with same email");
        try {
            adminFasade.addCustomer(new Customer("israel", "levi", "david@ori", "9876543"));
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("===================update customer (number 1) ==================");
        Customer customer1;
        try {
            customer1 = adminFasade.getOneCustomer(1);
            customer1.setFirstName("israel");
            customer1.setLastName("cohen");
            customer1.setPassword("56gh789j");
            customer1.setEmail("eeeee@wwww");
            adminFasade.updateCustomer(customer1);
            System.out.println(adminFasade.getOneCustomer(1));
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("============bonus- try to update email that exists");
        try {
            customer1 = adminFasade.getOneCustomer(2);
            customer1.setEmail("yehushua@rozenberg");
            adminFasade.updateCustomer(customer1);
            System.out.println(adminFasade.getOneCustomer(2));
        } catch (AdminException e) {
            System.out.println(e);
        }


        hardCodeCouponsPurchasesInsertInto();


        System.out.println();
        System.out.println("============get one customer (num 1) by id [can see also all coupons in array field] ============");
        Customer customer;
        try {
            customer = adminFasade.getOneCustomer(1);
            System.out.println(customer);
        } catch (AdminException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("==================get all customers=================");
        adminFasade.getAllCustomers().forEach(System.out::println);


        System.out.println();
        System.out.println("=================delete company (num 2)==================");
        adminFasade.deleteCompany(2);
        adminFasade.getAllCompanies().forEach(System.out::println);
        //coupon id 2 belongs to this company. show that all coupons + customer purchases are deleted
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        System.out.println("all coupons" + couponsDBDAO.getAllCoupons());
        System.out.println("all coupons purchases:\n" + couponsDBDAO.getAllCouponsPurchases());


        System.out.println();
        System.out.println("===================delete customer (number 1) ==================");
        adminFasade.deleteCustomer(1);
        adminFasade.getAllCustomers().forEach(System.out::println);
        //show that all coupons purchace are deleted
        System.out.println(couponsDBDAO.getAllCouponsPurchases());


    }

    private static void hardCodeCouponsInsertInto() {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        couponsDBDAO.addCoupon(new Coupon(1, Category.FOOD, "pizza", "1+1", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 50, 100, "IN COMPUTER"));
        couponsDBDAO.addCoupon(new Coupon(2, Category.ELECTRICITY, "mazgan", "1+1", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 100, 100, "IN COMPUTER"));
        couponsDBDAO.addCoupon(new Coupon(3, Category.RESTRAURANT, "roladin", "1+1", Date.valueOf("2021-1-1"), Date.valueOf("2021-12-12"), 200, 100, "IN COMPUTER"));
    }

    private static void hardCodeCouponsPurchasesInsertInto() {
        try {
            NewDBUtils.runSimpleQuery("INSERT INTO `couponsDB`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (1,1)");
            NewDBUtils.runSimpleQuery("INSERT INTO `couponsDB`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (2,1)");
            NewDBUtils.runSimpleQuery("INSERT INTO `couponsDB`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (2,2)");
            NewDBUtils.runSimpleQuery("INSERT INTO `couponsDB`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (3,3)");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
