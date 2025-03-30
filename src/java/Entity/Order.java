/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

public class Order {
    private int id;
    private User userId;
    private Date orderDate;
    private String recipientName;
    private String status;
    private String phone;
    private String address;
    private String paymentMethod;
    
    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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

    public void setRecipientName(String recipientName) {
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

    public Order(int id, User userId, Date orderDate, String recipientName, String status, String phone, String address, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.recipientName = recipientName;
        this.status = status;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

   public Order( User userId, Date orderDate, String recipientName, String status, String phone, String address, String paymentMethod) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.recipientName = recipientName;
        this.status = status;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
    
}

