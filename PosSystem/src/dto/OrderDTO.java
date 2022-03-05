package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class OrderDTO implements Serializable {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, LocalDate orderDate, String custId, List<OrderDetailDTO> orderDetail) {
        OrderId = orderId;
        OrderDate = orderDate;
        CustId = custId;
        this.orderDetail = orderDetail;
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

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
