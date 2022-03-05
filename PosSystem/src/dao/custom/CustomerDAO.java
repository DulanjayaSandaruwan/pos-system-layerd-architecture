package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public interface CustomerDAO extends CrudDAO<Customer, String> {

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
}
