package com.hexaware.repository;

import com.hexaware.model.Product;
import com.hexaware.model.Product.Category;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository class for Product entities with in-memory implementation
 */
@Repository
public class ProductRepository {
    
    private final List<Product> products;
    private Integer currentId = 1;

    /**
     * Constructor initializes the repository with 20 random products
     */
    public ProductRepository() {
        this.products = new ArrayList<>();
        initializeProducts();
    }

    /**
     * Initialize the repository with 20 random products
     */
    private void initializeProducts() {
        // Furniture items
        createProduct("Office Desk", 299.99f, Category.FURNITURE);
        createProduct("Gaming Chair", 199.99f, Category.FURNITURE);
        createProduct("Bookshelf", 149.99f, Category.FURNITURE);
        createProduct("Dining Table", 399.99f, Category.FURNITURE);
        createProduct("Sofa Set", 899.99f, Category.FURNITURE);
        createProduct("Coffee Table", 129.99f, Category.FURNITURE);
        createProduct("Bed Frame", 499.99f, Category.FURNITURE);
        createProduct("Wardrobe", 599.99f, Category.FURNITURE);
        createProduct("TV Stand", 179.99f, Category.FURNITURE);
        createProduct("Office Cabinet", 249.99f, Category.FURNITURE);

        // Electronics items
        createProduct("Smart TV", 799.99f, Category.ELECTRONICS);
        createProduct("Laptop", 1299.99f, Category.ELECTRONICS);
        createProduct("Smartphone", 699.99f, Category.ELECTRONICS);
        createProduct("Wireless Earbuds", 129.99f, Category.ELECTRONICS);
        createProduct("Gaming Console", 499.99f, Category.ELECTRONICS);
        createProduct("Tablet", 399.99f, Category.ELECTRONICS);
        createProduct("Smart Watch", 249.99f, Category.ELECTRONICS);
        createProduct("Bluetooth Speaker", 79.99f, Category.ELECTRONICS);
        createProduct("Monitor", 299.99f, Category.ELECTRONICS);
        createProduct("Wireless Mouse", 29.99f, Category.ELECTRONICS);
    }

    /**
     * Helper method to create and add a product
     */
    private Product createProduct(String name, float price, Category category) {
        Product product = new Product(currentId++, name, price, category, LocalDateTime.now());
        products.add(product);
        return product;
    }

    /**
     * Find all products in the repository
     *
     * @return List of all products
     */
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    /**
     * Find a product by its ID
     *
     * @param id Product ID to find
     * @return Optional containing the product if found, empty otherwise
     */
    public Optional<Product> findById(Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Save a new product or update an existing one
     *
     * @param product Product to save or update
     * @return Saved or updated product
     */
    public Product save(Product product) {
        if (product.getId() == null) {
            // New product
            product.setId(currentId++);
            product.setDateOfUpload(LocalDateTime.now());
            products.add(product);
        } else {
            // Update existing product
            deleteById(product.getId());
            products.add(product);
        }
        return product;
    }

    /**
     * Delete a product by its ID
     *
     * @param id ID of the product to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Integer id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    /**
     * Filter products by category
     *
     * @param category Category to filter by
     * @return List of products in the specified category
     */
    public List<Product> filterByCategory(Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
    }
}