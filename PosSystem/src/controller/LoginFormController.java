package controller;

import bo.BoFactory;
import bo.custom.UsersBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entity.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public class LoginFormController {
    public AnchorPane root;
    public JFXButton btnLogin;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXComboBox<String> cmbUserRole;

    public static String userRole;
    public static String userName;

    UsersBO usersBO = (UsersBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USERS);

    public void initialize(){
        ObservableList items = cmbUserRole.getItems();
        items.add("Manager");
        items.add("Reception");
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        try {
            int i = usersBO.searchUserRole(new Users(txtUserName.getText(), txtPassword.getText(), cmbUserRole.getSelectionModel().getSelectedItem()));
            userName = txtUserName.getText();
            userRole = cmbUserRole.getSelectionModel().getSelectedItem();
            if(i == 1){
                Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml")));
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Home Page");
                primaryStage.centerOnScreen();
            }else{
                new Alert(Alert.AlertType.ERROR,"Please Enter Correct Details").showAndWait();
                cmbUserRole.getSelectionModel().select(null);
                txtUserName.clear();
                txtPassword.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//       Parent parent = FXMLLoader.load(this.getClass().getResource("../view/MainForm.fxml"));
//        Scene scene = new Scene(parent);
//        Stage primaryStage = (Stage) this.root.getScene().getWindow();
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Main Form");
//        primaryStage.centerOnScreen();

    }
}
