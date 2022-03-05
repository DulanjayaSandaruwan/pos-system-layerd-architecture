package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-26
 **/
public class MainFormController {
    public AnchorPane root2;

    public Label lblTitle;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnManageCustomers;
    public JFXButton btnPlaceOrders;
    public JFXButton btnManageItems;
    public JFXButton btnHome;
    public JFXButton btnRegisterNewUser;
    public JFXButton btnReports;
    public JFXButton btnLogout;
    public Label lblMenu;
    public Label lblDescription;

   public void initialize(){
       String userRole = LoginFormController.userRole;
        if(userRole.equals("Reception")){
            btnReports.setDisable(true);
            btnRegisterNewUser.setDisable(true);
        }
        String name = LoginFormController.userName;
        lblTitle.setText("Hi " + name + " Welcome To The Sale !");
   }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            switch (button.getId()) {
                case "btnHome":
                    lblMenu.setText("Home");
                    lblDescription.setText("Please select one of above main operations to proceed");
                    break;
                case "btnManageCustomers":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "btnManageItems":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "btnPlaceOrders":
                    lblMenu.setText("Place Order");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "btnRegisterNewUser":
                    lblMenu.setText("Register New User");
                    lblDescription.setText("Click if you want to add new users");
                    break;
                case "btnReports":
                    lblMenu.setText("Reports");
                    lblDescription.setText("Click if you want to check reports");
                    break;
                case "btnLogout":
                    lblMenu.setText("Log Out");
                    lblDescription.setText("Click if you want to log out");
            }

        }
    }

    @FXML
    public void playMouseExitAnimation(MouseEvent event) {
        Button button = (Button) event.getSource();

        button.setEffect(null);
        lblMenu.setText("Welcome");
        lblDescription.setText("Please select one of above main operations to proceed");
    }

    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();

            Parent root = null;

            switch (button.getId()) {
                case "btnHome":
                    root = null;
                    break;
                case "btnManageCustomers":
                    root = FXMLLoader.load(this.getClass().getResource("../view/ManageCustomersForm.fxml"));
                    root2.getChildren().clear();
                    root2.getChildren().add(root);
                    break;
                case "btnManageItems":
                    root = FXMLLoader.load(this.getClass().getResource("../view/ManageItemForm.fxml"));
                    root2.getChildren().clear();
                    root2.getChildren().add(root);
                    break;
                case "btnPlaceOrders":
                    root = FXMLLoader.load(this.getClass().getResource("../view/PlaceOrderForm.fxml"));
                    root2.getChildren().clear();
                    root2.getChildren().add(root);
                    break;
                case "btnRegisterNewUser":
                    root = FXMLLoader.load(this.getClass().getResource("../view/ManageUsersForm.fxml"));
                    root2.getChildren().clear();
                    root2.getChildren().add(root);
                    break;
                case "btnReports":
                    root = null;
                    break;
                case "btnLogout":
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                        Scene scene = new Scene(parent);
                        Stage primaryStage = (Stage) this.root2.getScene().getWindow();
                        primaryStage.setScene(scene);
                        primaryStage.setTitle("Login Form");
                        primaryStage.centerOnScreen();
                    }
            }
        }
    }
}



