package entity;

import java.time.LocalDate;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class Order {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;

    public Order() {
    }

    public Order(String orderId, LocalDate orderDate, String custId) {
        OrderId = orderId;
        OrderDate = orderDate;
        CustId = custId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }
}
