/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Admin
 */
public class Product implements Comparable<Product> {

    private int id;
    private int categoryId;
    private String name;
    private double price;
    private String description;
    private String image;
    private int quantity;
    private String detail;
    private String categoryName;

    public Product() {
    }

     public Product(int id, int categoryId, String name, double price, String description, String image, int quantity, String detail) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public Product(int id, int categoryId, String name, double price, String description, String image, int quantity, String detail, String categoryName) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.detail = detail;
        this.categoryName = categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int compareTo(Product pr) {
        return Integer.compare(this.id, pr.id);
    }

}
