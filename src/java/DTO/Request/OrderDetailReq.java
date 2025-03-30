package DTO.Request;

import java.util.Date;

public class OrderDetailReq {
    private int id;
    private int userId;  // Đổi từ user_id thành userId theo camelCase
    private Date orderDate;
    private String recipientName; // Đổi từ recipient_name thành recipientName
    private String status;
    private String phone;
    private String address;
    private String paymentMethod;
    private Double totalAmount; // Đổi từ total_amount thành totalAmount

    // Constructor
    public OrderDetailReq() {}

    // Getter & Setter chuẩn theo JavaBeans
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) { // Sửa lại từ setRecipient_name()
        this.recipientName = recipientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
