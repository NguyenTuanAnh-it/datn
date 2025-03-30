/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Review {
    private int id;
    private int product_id;
    private int user_id;
    private String username; 
    private int rating;
    private String comment;
    private LocalDate review_date;

    public Review() {
    }
    public Date getReviewDateAsDate() {
        return Date.from(review_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Review(int id, int product_id, int user_id, String username, int rating, String comment, LocalDate review_date) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.review_date = review_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDate review_date) {
        this.review_date = review_date;
    }
    
    
}
