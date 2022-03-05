package dao.custom;

import dao.CrudDAO;
import entity.Users;

import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public interface UsersDAO extends CrudDAO<Users, String> {

    boolean ifUsersExist(String id) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
    public int searchUserRole(Users entity)throws Exception;

}
