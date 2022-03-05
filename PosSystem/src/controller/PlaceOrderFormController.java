package controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import view.tdm.CartTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class PlaceOrderFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXButton btnSave;
    public TableView<CartTm> tblOrderDetails;
    public Label lblId;
    public Label lblDate;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtPackSize;
    public JFXTextField txtDiscount;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblQty;
    public TableColumn tblUnitPrice;
    public TableColumn tblDiscount;
    public TableColumn tblTotal;
    public TableColumn tblDelete;
    private String orderId;

    int selectedIndex = -1;

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASEORDER);

    public void initialize() {

        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblDelete.setCellValueFactory(new PropertyValueFactory<>("button"));
        tblOrderDetails.refresh();

        tblOrderDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex = (int) newValue;
            ObservableList<CartTm> items = tblOrderDetails.getItems();
            for (CartTm tm : items
            ) {
                if (tm.getButton().equals(tblOrderDetails.getItems().get(selectedIndex).getButton())) {
                    JFXButton button = tm.getButton();
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            tblOrderDetails.getItems().remove(selectedIndex);
                        }
                    });
                }
            }
        });


        try {
            setDataToCustomerCombo();
            setDataToItemCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        orderId=generateNewOrderId();
        lblId.setText(orderId);
    }

    public void setDataToCustomerCombo() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = purchaseOrderBO.getAllCustomers();
        for (CustomerDTO d : allCustomers
        ) {
            cmbCustomerId.getItems().add(d.getCustId());
        }
    }

    public void setDataToItemCombo() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = purchaseOrderBO.getAllItems();
        for (ItemDTO d : allItems
        ) {
            cmbItemCode.getItems().add(d.getItemCode());
        }
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {

    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getValue().toString();
        String itemDescription = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double priceWithoutDiscount = unitPrice * qty;
        double discount = 0.0;
        JFXButton button = new JFXButton("delete");
        button.setStyle("-fx-background-color: green;-fx-text-fill: white");

        if (txtDiscount.getText().isEmpty()) {
            discount = 0.0;
        } else {
            double discountInPercentage = Double.parseDouble(txtDiscount.getText());
            double d = discountInPercentage / 100;
            discount = priceWithoutDiscount * d;
        }
        double total = priceWithoutDiscount - discount;

        if (qty > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            return;
        }
        boolean b = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));
        if (b) {
            CartTm cart = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();
            cart.setQty(cart.getQty() + qty);
            cart.setTotal(total + cart.getTotal());
            tblOrderDetails.refresh();
        } else {
            CartTm tm = new CartTm(itemCode, itemDescription, qty, unitPrice, discount, total, button);
            tblOrderDetails.getItems().add(tm);
        }
        double totalCost = calculateTotalCost();
        lblTotal.setText(String.valueOf(totalCost));

    }

    public double calculateTotalCost() {
        double total = 0.0;
        for (CartTm tm : tblOrderDetails.getItems()
        ) {
            total += tm.getTotal();
        }
        return total;
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        String customerId = null;
        if (cmbCustomerId.getValue() != null) {
            customerId = cmbCustomerId.getValue();
        }
        try {
            boolean b = saveOrder(lblId.getText(), customerId, tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(tm.getItemCode(), lblId.getText(), tm.getQty(),tm.getUnitPrice(),tm.getDiscount(),tm.getTotal()-tm.getDiscount())).collect(Collectors.toList()));
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed", ButtonType.CLOSE).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtDiscount.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtQty.clear();
        lblTotal.setText("0.00");
    }

    private boolean saveOrder(String orderId, String customerId, List<OrderDetailDTO> collect) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> detailDTOS = new ArrayList<>();
        for (OrderDetailDTO dto : collect
        ) {
            detailDTOS.add(new OrderDetailDTO(dto.getItemCode(), dto.getOrderId(), dto.getOrderQty(), dto.getPricePerQty(),dto.getDiscount(),dto.getCost()));
        }
        OrderDTO orderDTO = new OrderDTO(orderId, LocalDate.parse(lblDate.getText()), customerId, collect);
        return purchaseOrderBO.purchaseOrder(orderDTO);
    }

    public void getItemDetails(ActionEvent actionEvent) {
        if (cmbItemCode.getValue()!=null) {
            String itemCode = cmbItemCode.getValue().toString();
            try {
                ItemDTO itemDTO = purchaseOrderBO.searchItem(itemCode);
                txtDescription.setText(itemDTO.getDescription());
                txtPackSize.setText(itemDTO.getPackSize());
                txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCustomerDetails(ActionEvent actionEvent) {
        if (cmbCustomerId.getValue()!=null) {
            String customerId = cmbCustomerId.getValue().toString();
            try {
                CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(customerId);
                txtCustomerName.setText(customerDTO.getCustname());
                txtCustomerAddress.setText(customerDTO.getCustAddress());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
