package view.tdm;

import java.math.BigDecimal;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class OrderDetailTM {
    private String ItemCode;
    private String OrderId;
    private int OrderQty;
    private double Discount;
    private BigDecimal total;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String itemCode, String orderId, int orderQty, double discount, BigDecimal total) {
        ItemCode = itemCode;
        OrderId = orderId;
        OrderQty = orderQty;
        Discount = discount;
        this.total = total;
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

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
