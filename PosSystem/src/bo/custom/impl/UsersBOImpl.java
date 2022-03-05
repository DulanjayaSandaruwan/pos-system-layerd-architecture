package bo.custom.impl;

import bo.BoFactory;
import bo.custom.UsersBO;
import dao.DAOFactory;
import dao.custom.UsersDAO;
import dto.UsersDTO;
import entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public class UsersBOImpl implements UsersBO {

    UsersDAO usersDAO = (UsersDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USERS);

    @Override
    public ArrayList<UsersDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UsersDTO> allUsers = new ArrayList<>();
        ArrayList<Users> all = usersDAO.getAll();
        for (Users users : all) {
            allUsers.add(new UsersDTO(
                    users.getUserId(),
                    users.getUserName(),
                    users.getNic(),
                    users.getAddress(),
                    users.getContact(),
                    users.getEmail(),
                    users.getPassword(),
                    users.getUserRole()
                    ));
        }
        return allUsers;
    }

    @Override
    public boolean addUsers(UsersDTO usersDTO) throws SQLException, ClassNotFoundException {
        Users users = new Users(
                usersDTO.getId(),
                usersDTO.getName(),
                usersDTO.getNic(),
                usersDTO.getAddress(),
                usersDTO.getContact(),
                usersDTO.getEmail(),
                usersDTO.getPassword(),
                usersDTO.getUserRole()
        );
        return usersDAO.add(users);
    }

    @Override
    public boolean updateUsers(UsersDTO usersDTO) throws SQLException, ClassNotFoundException {
        return usersDAO.update(new Users(
                usersDTO.getId(),
                usersDTO.getName(),
                usersDTO.getNic(),
                usersDTO.getAddress(),
                usersDTO.getContact(),
                usersDTO.getEmail(),
                usersDTO.getPassword(),
                usersDTO.getUserRole()
        ));
    }

    @Override
    public boolean ifUsersExist(String id) throws SQLException, ClassNotFoundException {
        return usersDAO.ifUsersExist(id);
    }

    @Override
    public boolean deleteUsers(String id) throws SQLException, ClassNotFoundException {
        return usersDAO.delete(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return usersDAO.generateNewID();
    }

    @Override
    public int searchUserRole(Users entity) throws Exception {
        return usersDAO.searchUserRole(entity);
    }
}
