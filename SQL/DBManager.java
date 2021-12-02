package SQL;

import java.sql.SQLException;

/**
 * Data base  Manager class
 * Creates the scheme and tables of the sql
 */
public class DBManager {
    /**
     * connection DB String
     * URL string
     */
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    /**
     * USER_NAME string
     */
    public static final String USER_NAME = "root";
    /**
     * PASSWORD string
     */
    public static final String PASSWORD = "12345678";

    /**
     * create database String
     */
    private static final String CREATE_DB = "CREATE SCHEMA if not exists couponsDB";
    /**
     * drop database String
     */
    private static final String DROP_DB = "DROP schema if exists `couponsdb`";

    /**
     * create & drop tables Strings:
     * create companies table String
     */
    private static final String CREATE_TABLE_COMPANY =
            "CREATE TABLE if not exists`couponsDB`.`companies`" +
                    "(`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NULL," +
                    "`email` VARCHAR(45) NULL," +
                    "`password` VARCHAR(45) NULL," +
                    "PRIMARY KEY (`id`));";

    /**
     * create customers table String
     */
    private static final String CREATE_TABLE_CUSTOMERS =
            "CREATE TABLE if not exists `couponsDB`.`customers` "
                    + "(`id` INT NOT NULL AUTO_INCREMENT," +
                    "`FIRST_NAME` VARCHAR(45) NULL," +
                    "`LAST_NAME` VARCHAR(45) NULL," +
                    "`EMAIL` VARCHAR(45) NULL," +
                    "`PASSWORD` VARCHAR(45) NULL," +
                    "PRIMARY KEY (`id`));";

    /**
     * create categories table String - enum table
     */
    private static final String CREATE_TABLE_CATAGORIES =
            "CREATE TABLE if not exists`couponsDB`.`categories`" +
                    "(`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NULL," +
                    "PRIMARY KEY (`id`));";

    /**
     * Puts categories into the categories table String
     */
    private static final String INSERT_INTO_CATEGORY1 =
            "INSERT INTO `couponsDB`.`categories` (`name`) VALUES ('FOOD')";
    private static final String INSERT_INTO_CATEGORY2 =
            "INSERT INTO `couponsDB`.`categories` (`name`) VALUES ('ELECTRICITY')";
    private static final String INSERT_INTO_CATEGORY3 =
            "INSERT INTO `couponsDB`.`categories` (`name`) VALUES ('RESTRAURANT')";
    private static final String INSERT_INTO_CATEGORY4 =
            "INSERT INTO `couponsDB`.`categories` (`name`) VALUES ('VACATION')";

    //create coupons table String
    private static final String CREATE_TABLE_COUPONS =
            "CREATE TABLE if not exists`couponsDB`.`coupons` " +
                    "(`id` INT NOT NULL AUTO_INCREMENT," +
                    "`ID_COMPANY` INT NULL," +
                    "`iD_CATEGORY` INT NULL," +
                    "`title` VARCHAR(45) NULL," +
                    "`DESCRIPTION` VARCHAR(45) NULL," +
                    "`START_DATE` DATE NULL," +
                    "`END_DATE` DATE NULL," +
                    "`AMOUNT` INT NULL," +
                    "`PRICE` DOUBLE NULL," +
                    "`IMAGE` VARCHAR(45) NULL," +
                    "PRIMARY KEY (`id`)," +
                    "FOREIGN KEY (`ID_COMPANY`)" +
                    " REFERENCES `companies` (`id`) ON DELETE CASCADE," +
                    "FOREIGN KEY (`iD_CATEGORY`)" +
                    " REFERENCES `categories` (`id`) ON DELETE CASCADE);";

    //create customers_vs_coupons table String - A table documenting all coupon purchases
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS =
            "CREATE TABLE if not exists `couponsDB`.`customers_vs_coupons` " +
                    "(`customer_id` INT NOT NULL," +
                    "`coupon_id` INT NOT NULL," +
                    " PRIMARY KEY (`customer_id`, `coupon_id`)," +
                    " INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
                    " FOREIGN KEY (`customer_id`)" +
                    " REFERENCES `customers` (`id`) ON DELETE CASCADE," +
                    " FOREIGN KEY (`coupon_id`)" +
                    " REFERENCES `coupons` (`id`) ON DELETE CASCADE)";

    /**
     * create data base
     */
    public static void createDataBase() {
        try {
            NewDBUtils.runSimpleQuery(CREATE_DB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * create Tables in sql: companies, customers, categories(enum table), coupons, customers_vs_coupons (A table documenting all coupon purchases)
     * Puts categories into the categories table
     */
    public static void createTables() {
        try {
            NewDBUtils.runSimpleQuery(CREATE_TABLE_COMPANY);
            NewDBUtils.runSimpleQuery(CREATE_TABLE_CUSTOMERS);
            NewDBUtils.runSimpleQuery(CREATE_TABLE_CATAGORIES);
            NewDBUtils.runSimpleQuery(CREATE_TABLE_COUPONS);
            NewDBUtils.runSimpleQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
            NewDBUtils.runSimpleQuery(INSERT_INTO_CATEGORY1);
            NewDBUtils.runSimpleQuery(INSERT_INTO_CATEGORY2);
            NewDBUtils.runSimpleQuery(INSERT_INTO_CATEGORY3);
            NewDBUtils.runSimpleQuery(INSERT_INTO_CATEGORY4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes the entire schema from the sql
     */
    public static void dropdb() {
        try {
            NewDBUtils.runSimpleQuery(DROP_DB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
