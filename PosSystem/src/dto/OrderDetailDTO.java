package dto;

import java.io.Serializable;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class OrderDetailDTO implements Serializable {
    private String ItemCode;
    private String OrderId;
    private int OrderQty;
    private double PricePerQty;
    private double Discount;
    private double Cost;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String itemCode, String orderId, int orderQty, double pricePerQty, double discount, double cost) {
        ItemCode = itemCode;
        OrderId = orderId;
        OrderQty = orderQty;
        PricePerQty = pricePerQty;
        Discount = discount;
        Cost = cost;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getPricePerQty() {
        return PricePerQty;
    }

    public void setPricePerQty(double pricePerQty) {
        PricePerQty = pricePerQty;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }
}
