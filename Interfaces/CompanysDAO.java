package Interfaces;

import Beans.Company;

import java.util.ArrayList;

public interface CompanysDAO {

    boolean isCompanyExists(String email, String password);

    void addCompany(Company company);

    void updateCompany(Company company);

    void deleteCompany(int id);

    ArrayList<Company> getAllCompanys();

    Company getOneCompany(int id);

}
