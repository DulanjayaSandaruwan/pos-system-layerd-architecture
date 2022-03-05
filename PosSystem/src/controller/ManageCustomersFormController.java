package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tdm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-27
 **/
public class ManageCustomersFormController {

    public Label lblCustomerId;
    public JFXButton btnAddNewCustomer;
    public JFXTextField txtCustTitle;
    public JFXTextField txtCustName;
    public JFXTextField txtCustAddress;
    public JFXTextField txtCustCity;
    public JFXTextField txtProvince;
    public JFXPasswordField txtPostalCode;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<CustomerTM> tblCustomerDetails;
    public TableColumn colCustId;
    public TableColumn colCustTitle;
    public TableColumn colCustName;
    public TableColumn colCustAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;

    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public void initialize() {
        tblCustomerDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CustId"));
        tblCustomerDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustTitle"));
        tblCustomerDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Custname"));
        tblCustomerDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("CustAddress"));
        tblCustomerDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("City"));
        tblCustomerDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Province"));
        tblCustomerDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("PostalCode"));

        initUI();

        tblCustomerDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);
            if (newValue != null) {
                lblCustomerId.setText(newValue.getCustId());
                txtCustTitle.setText(newValue.getCustTitle());
                txtCustName.setText(newValue.getCustname());
                txtCustAddress.setText(newValue.getCustAddress());
                txtCustCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode());
                lblCustomerId.setDisable(false);
                txtCustTitle.setDisable(false);
                txtCustName.setDisable(false);
                txtCustAddress.setDisable(false);
                txtCustCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);
            }
        });

        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCustomerDetails.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCustomerDetails.getItems().add(new CustomerTM(
                        customer.getCustId(),
                        customer.getCustTitle(),
                        customer.getCustname(),
                        customer.getCustAddress(),
                        customer.getCity(),
                        customer.getProvince(),
                        customer.getPostalCode()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        lblCustomerId.setText("");
        txtCustTitle.clear();
        txtCustName.clear();
        txtCustAddress.clear();
        txtCustCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        lblCustomerId.setDisable(true);
        txtCustTitle.setDisable(true);
        txtCustName.setDisable(true);
        txtCustAddress.setDisable(true);
        txtCustCity.setDisable(true);
        txtProvince.setDisable(true);
        txtPostalCode.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        lblCustomerId.setDisable(false);
        txtCustTitle.setDisable(false);
        txtCustName.setDisable(false);
        txtCustAddress.setDisable(false);
        txtCustCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);
        lblCustomerId.setText("");
        lblCustomerId.setText(generateNewId());
        txtCustTitle.clear();
        txtCustName.clear();
        txtCustAddress.clear();
        txtCustCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCustTitle.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomerDetails.getSelectionModel().clearSelection();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = lblCustomerId.getText();
        String title = txtCustTitle.getText();
        String name = txtCustName.getText();
        String address = txtCustAddress.getText();
        String city = txtCustCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                CustomerDTO customerDTO = new CustomerDTO(id, title, name, address, city, province, postalCode);
                customerBO.addCustomer(customerDTO);
                tblCustomerDetails.getItems().add(new CustomerTM(id, title, name, address, city, province, postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                CustomerDTO customerDTO = new CustomerDTO(id, title, name, address, city, province, postalCode);
                customerBO.updateCustomer(customerDTO);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomerDetails.getSelectionModel().getSelectedItem();
            selectedCustomer.setCustTitle(title);
            selectedCustomer.setCustname(name);
            selectedCustomer.setCustAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);
            tblCustomerDetails.refresh();
        }
        btnAddNewCustomer.fire();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = tblCustomerDetails.getSelectionModel().getSelectedItem().getCustId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCustomerDetails.getItems().remove(tblCustomerDetails.getSelectionModel().getSelectedItem());
            tblCustomerDetails.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewId() {
        try {
            return customerBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomerDetails.getItems().isEmpty()) {
            return "C001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }

    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomerDetails.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustId();
    }

}
