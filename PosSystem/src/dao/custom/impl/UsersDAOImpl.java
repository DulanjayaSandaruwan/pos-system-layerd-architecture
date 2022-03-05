package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.UsersDAO;
import entity.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public class UsersDAOImpl implements UsersDAO {

    @Override
    public boolean add(Users entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Users VALUES (?,?,?,?,?,?,?,?)",
                entity.getUserId(), entity.getUserName(), entity.getNic(), entity.getAddress(),
                entity.getContact(), entity.getEmail(), entity.getPassword(), entity.getUserRole());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Users WHERE UserId=?", id);
    }

    @Override
    public boolean update(Users entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Users SET UserName=?, Nic=?, Address=?, Contact=?, Email=?, Password=?, UserRole=? WHERE UserId=?",
                entity.getUserName(), entity.getNic(), entity.getAddress(),
                entity.getContact(), entity.getEmail(), entity.getPassword(),entity.getUserRole(), entity.getUserId());
    }

    @Override
    public Users search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Users WHERE UserId=?", id);
        rst.next();
        return new Users(id,
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6),
                rst.getString(7)
                );
    }

    @Override
    public ArrayList<Users> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Users> allUsers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Users");
        while (rst.next()) {
            allUsers.add(new Users(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
                    )
            );

        }
        return allUsers;
    }

    @Override
    public boolean ifUsersExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT UserId FROM Users WHERE UserId=?", id).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT UserId FROM Customer ORDER BY UserId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("UserId");
            int newCustomerId = Integer.parseInt(id.replace("U", "")) + 1;
            return String.format("U%03d", newCustomerId);
        } else {
            return "U001";
        }
    }

    @Override
    public int searchUserRole(Users entity) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Users WHERE UserName = ? and Password = ? and UserRole = ?", entity.getUserName(), entity.getPassword(), entity.getUserRole());
        if(rst.next()){
            return 1;
        }
        return 0;
    }


}
