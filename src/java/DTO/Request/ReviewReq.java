/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO.Request;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class ReviewReq {
    private int id;
    private String email;
    private String content;
    private LocalDate review_date;

    public ReviewReq() {
    }

    public Date getReviewDateAsDate() {
        return Date.from(review_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public ReviewReq(int id, String email, String content, LocalDate review_date) {
        this.id = id;
        this.email = email;
        this.content = content;
        this.review_date = review_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDate review_date) {
        this.review_date = review_date;
    }
    
    
    
            
}
