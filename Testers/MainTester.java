package Testers;

import SQL.ConnectionPool;
import SQL.DBManager;

public class MainTester {
    public static void main(String[] args) {
        testAll();
    }

    public static void testAll() {
        try {
            DBManager.dropdb();
            DBManager.createDataBase();
            DBManager.createTables();
            new AdminTester();
            new CompanyTester();
            new CustomerTester();
            new ThreadTester();
        } catch (Exception err) {
            System.out.println("GENERAL EXCEPTION");
        }

    }
}

