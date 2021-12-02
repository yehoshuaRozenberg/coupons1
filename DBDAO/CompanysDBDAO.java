package DBDAO;

import Beans.Company;
import Interfaces.CompanysDAO;
import SQL.ConnectionPool;
import SQL.NewDBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Company Data base DAO class
 */
public class CompanysDBDAO implements CompanysDAO {
    private static final String ADD_COMPANY = "INSERT INTO `couponsDB`.`companies` (`name`,`email`,`password`) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `couponsDB`.`companies` SET `email`=?,`password`=? WHERE id=?";
    private static final String GET_ONE_COMPANY_BY_ID = "SELECT * FROM `couponsDB`.`companies` WHERE  `id`= ?";
    private static final String GET_ALL_COMPANY = "SELECT * FROM `couponsDB`.`companies`";
    private static final String DELETE_BY_ID = "DELETE FROM `couponsDB`.`companies` where id=?";
    private static final String IS_EXISTS_NAME = "SELECT EXISTS(SELECT 1 FROM couponsDB.companies WHERE name =? LIMIT 1)";
    private static final String IS_EXISTS_EMAIL = "SELECT EXISTS(SELECT 1 FROM couponsDB.companies WHERE email =? LIMIT 1)";
    private static final String LOG_IN = "SELECT EXISTS (SELECT 1 FROM `couponsDB`.`companies` WHERE email =? and password=? limit 1) ;";
    private static final String GET_ID_BY_EMAIL = "SELECT id FROM `couponsDB`.`companies` WHERE email= ?";

    /**
     * Receives an email and password of a company and checks in Sql if there is a company with the same email and password
     *
     * @param email    - company email
     * @param password - company password
     * @return boolean - true if  exists, false if not exist
     */
    @Override
    public boolean isCompanyExists(String email, String password) {
        boolean res = true;
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, email);
        params.put(2, password);
        try {
            if (!DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareMapStatement(LOG_IN, params)))) {
                res = false;
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("error: " + e);
        }
        return res;
    }

    /**
     * Receives an name and password of a company and checks in Sql if there is a company with the same email or password
     *
     * @param email - company email
     * @param name  - company name
     * @return boolean - true if  exists name or email, false If there is neither a name nor an email
     */
    public boolean isCompanyMailOrNameExists(String name, String email) {
        boolean res = true;
        try {
            if (!DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareStringStatement(IS_EXISTS_NAME, name))) &&
                    !DBDAO_Utils.booleanFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareStringStatement(IS_EXISTS_EMAIL, email))))
                res = false;
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }
        return res;
    }

    /**
     * Gets a company and adds it to the companies table in Sql
     *
     * @param company - the company we want to add
     */
    @Override
    public void addCompany(Company company) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(ADD_COMPANY, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }

    }

    /**
     * Gets a company, and by the id company, update it in the companies table in Sql
     *
     * @param company - id: the id of the company we want to update. email and password - The data we update
     */
    @Override
    public void updateCompany(Company company) {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, company.getEmail());
        params.put(2, company.getPassword());
        params.put(3, company.getId());
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareMapStatement(UPDATE_COMPANY, params));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }

    }

    /**
     * delete company from the company table in sql, by the id company
     *
     * @param id: the id of the company we want to delete
     */
    @Override
    public void deleteCompany(int id) {
        try {
            NewDBUtils.runStatment(NewDBUtils.prepareIntStatement(DELETE_BY_ID, id));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }
    }

    /**
     * Receives the data of all the companies from the  companies table in Sql, and creates an arrayLisst of companies from it
     *
     * @return arrayLisst of companies
     */
    @Override
    public ArrayList<Company> getAllCompanys() {
        ArrayList<Company> companies = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = NewDBUtils.runSimpleQueryGetResult(GET_ALL_COMPANY);
            while (resultSet.next()) {
                Company company = new Company(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                company.setId(resultSet.getInt(1));
                companies.add(company);
            }
        } catch (SQLException e) {
            System.out.println("Error in Sql: " + e);
        }
        return companies;
    }

    /**
     * Receives the data of one company (by id company) from the  companies table in Sql, and creates company from it
     *
     * @param id: the id of the company we want to get
     * @return the company
     */
    @Override
    public Company getOneCompany(int id) {
        Company company = null;
        ResultSet resultSet;
        try {
            resultSet = NewDBUtils.runStatmentGetResult(NewDBUtils.prepareIntStatement(GET_ONE_COMPANY_BY_ID, id));
            while (resultSet.next()) {
                company = new Company(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                company.setId(resultSet.getInt(1));
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }
        return company;
    }

    /**
     * Receives an id of company from the companies table, according to the company email
     *
     * @param email - the company email
     * @return id of this company
     */
    public static int getIdByEmail(String email) {
        int id = 0;
        try {
            id = DBDAO_Utils.intFromResult(NewDBUtils.runStatmentGetResult(NewDBUtils.prepareStringStatement(GET_ID_BY_EMAIL, email)));
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error in Sql: " + e);
        }
        return id;
    }


}
