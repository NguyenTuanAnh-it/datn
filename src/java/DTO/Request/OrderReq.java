/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO.Request;

/**
 *
 * @author ADMIN
 */
public class OrderReq {
    private int id;
    private Double total_amout;
    private String payment_method;

    public OrderReq() {
    }

    public OrderReq(int id, Double total_amout, String payment_method) {
        this.id = id;
        this.total_amout = total_amout;
        this.payment_method = payment_method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal_amout() {
        return total_amout;
    }

    public void setTotal_amout(Double total_amout) {
        this.total_amout = total_amout;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    
    
}
