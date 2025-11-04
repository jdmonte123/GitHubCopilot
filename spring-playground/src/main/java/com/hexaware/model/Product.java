package com.hexaware.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Product entity class representing a product in the system.
 */
public class Product {
    
    private Integer id;
    private String name;
    private float price;
    private Category category;
    private LocalDateTime dateOfUpload;

    /**
     * Enum representing product categories
     */
    public enum Category {
        FURNITURE,
        ELECTRONICS
    }

    /**
     * Default constructor
     */
    public Product() {
    }

    /**
     * Parameterized constructor
     *
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param category Product category
     * @param dateOfUpload Date when the product was uploaded
     */
    public Product(Integer id, String name, float price, Category category, LocalDateTime dateOfUpload) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.dateOfUpload = dateOfUpload;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(LocalDateTime dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    /**
     * Equals method based on product ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    /**
     * HashCode method based on product ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * ToString method for debugging and logging
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", dateOfUpload=" + dateOfUpload +
                '}';
    }
}