package bo.custom;

import bo.SuperBO;
import dto.UsersDTO;
import entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public interface UsersBO extends SuperBO {

    ArrayList<UsersDTO> getAllUsers() throws SQLException, ClassNotFoundException;

    boolean addUsers(UsersDTO usersDTO) throws SQLException, ClassNotFoundException;

    boolean updateUsers(UsersDTO usersDTO) throws SQLException, ClassNotFoundException;

    boolean ifUsersExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteUsers(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    public int searchUserRole(Users entity)throws Exception;

}
