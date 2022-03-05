package dao.custom;

import dao.CrudDAO;
import entity.Item;
import entity.Users;

import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public interface ItemDAO extends CrudDAO<Item, String> {
    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
