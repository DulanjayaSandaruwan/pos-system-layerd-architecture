package controller;

import bo.BoFactory;
import bo.custom.UsersBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UsersDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tdm.UsersTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-26
 **/
public class ManageUsersFormController {
    public Label lblUserID;
    public JFXButton btnAddNewUser;
    public JFXTextField txtUserFullName;
    public JFXTextField txtUserNIC;
    public JFXTextField txtUserAddress;
    public JFXTextField txtUserContact;
    public JFXTextField txtUserMail;
    public JFXPasswordField txtUserNewPassword;
    public JFXPasswordField txtUserConfirmPassword;
    public JFXComboBox<String> cmbUserRole;
    public JFXButton btnSaveUsers;
    public JFXButton btnDeleteUsers;
    public TableView<UsersTM> tblUsers;
    public TableColumn colUsers;
    public TableColumn colUserID;

    UsersBO usersBO = (UsersBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USERS);

    public void initialize() {
        loadUserCombo();

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));

        initUI();

        tblUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteUsers.setDisable(newValue == null);
            btnSaveUsers.setText(newValue != null ? "Update" : "Save");
            btnSaveUsers.setDisable(newValue == null);
            if (newValue != null) {
                lblUserID.setText(newValue.getId());
                txtUserFullName.setText(newValue.getName());
                txtUserNIC.setText(newValue.getNic());
                txtUserAddress.setText(newValue.getAddress());
                txtUserContact.setText(newValue.getContact());
                txtUserMail.setText(newValue.getEmail());
                txtUserConfirmPassword.setText(newValue.getPassword());
                txtUserFullName.setDisable(false);
                txtUserNIC.setDisable(false);
                txtUserAddress.setDisable(false);
                txtUserContact.setDisable(false);
                txtUserNewPassword.setDisable(false);
                txtUserConfirmPassword.setDisable(false);
                txtUserMail.setDisable(false);
                cmbUserRole.setDisable(false);
            }
        });
        loadAllUsers();
    }


    private void loadAllUsers() {
        tblUsers.getItems().clear();
        try {
            ArrayList<UsersDTO> allUsers = usersBO.getAllUsers();
            for (UsersDTO usersDTO : allUsers) {
                tblUsers.getItems().add(new UsersTM(
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
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void initUI() {
        lblUserID.setText("");
        txtUserFullName.clear();
        txtUserNIC.clear();
        txtUserAddress.clear();
        txtUserContact.clear();
        txtUserMail.clear();
        txtUserNewPassword.clear();
        txtUserConfirmPassword.clear();
        cmbUserRole.valueProperty().set(null);
        txtUserFullName.setDisable(true);
        txtUserNIC.setDisable(true);
        txtUserAddress.setDisable(true);
        txtUserNewPassword.setDisable(true);
        txtUserConfirmPassword.setDisable(true);
        txtUserContact.setDisable(true);
        txtUserMail.setDisable(true);
        cmbUserRole.setDisable(true);
        btnSaveUsers.setDisable(true);
        btnDeleteUsers.setDisable(true);
    }


    private void loadUserCombo() {
        ObservableList<String> items = cmbUserRole.getItems();
        items.add("Manager");
        items.add("Reception");
    }


    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        txtUserFullName.setDisable(false);
        txtUserNIC.setDisable(false);
        txtUserAddress.setDisable(false);
        txtUserContact.setDisable(false);
        txtUserMail.setDisable(false);
        txtUserNewPassword.setDisable(false);
        txtUserConfirmPassword.setDisable(false);
        cmbUserRole.setDisable(false);
        lblUserID.setText("");
        lblUserID.setText(generateNewId());
        txtUserFullName.clear();
        txtUserNIC.clear();
        txtUserAddress.clear();
        txtUserContact.clear();
        txtUserMail.clear();
        txtUserNewPassword.clear();
        txtUserConfirmPassword.clear();
        txtUserFullName.requestFocus();
        btnSaveUsers.setDisable(false);
        btnSaveUsers.setText("Save");
        tblUsers.getSelectionModel().clearSelection();
    }


    private String generateNewId() {
        try {
            return usersBO.generateNewID();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblUsers.getItems().isEmpty()) {
            return "U001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("U", "")) + 1;
            return String.format("U%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<UsersTM> tempUsersList = new ArrayList<>(tblUsers.getItems());
        Collections.sort(tempUsersList);
        return tempUsersList.get(tempUsersList.size() - 1).getId();
    }

    public void btnSaveUsersOnAction(ActionEvent actionEvent) {
        String id = lblUserID.getText();
        String name = txtUserFullName.getText();
        String nic = txtUserNIC.getText();
        String address = txtUserAddress.getText();
        String contact = txtUserContact.getText();
        String eMail = txtUserMail.getText();
        String cPassword = txtUserConfirmPassword.getText();
        String uRole = cmbUserRole.getSelectionModel().getSelectedItem();

        if (btnSaveUsers.getText().equalsIgnoreCase("save")) {
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                UsersDTO usersDTO = new UsersDTO(id, name, nic, address, contact, eMail, cPassword, uRole);
                usersBO.addUsers(usersDTO);
                tblUsers.getItems().add(new UsersTM(id, name, nic, address, contact, eMail, cPassword, uRole));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                UsersDTO usersDTO = new UsersDTO(id, name, nic, address, contact, eMail, cPassword, uRole);
                usersBO.updateUsers(usersDTO);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            UsersTM selectedUsers = tblUsers.getSelectionModel().getSelectedItem();
            selectedUsers.setName(name);
            selectedUsers.setAddress(address);
            tblUsers.refresh();
        }
        btnAddNewUser.fire();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return usersBO.ifUsersExist(id);
    }

    public void btnDeleteUsersOnAction(ActionEvent actionEvent) {
        String id = tblUsers.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            usersBO.deleteUsers(id);
            tblUsers.getItems().remove(tblUsers.getSelectionModel().getSelectedItem());
            tblUsers.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
