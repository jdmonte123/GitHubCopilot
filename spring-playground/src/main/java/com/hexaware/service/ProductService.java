package com.hexaware.service;

import com.hexaware.model.Product;
import com.hexaware.model.Product.Category;
import com.hexaware.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing product operations
 */
@Service
public class ProductService {

    private static final float PRICE_THRESHOLD = 100.0f;
    private final ProductRepository productRepository;

    /**
     * Constructor for dependency injection
     *
     * @param productRepository Repository for product operations
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get all products
     *
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get a product by its ID
     *
     * @param id Product ID
     * @return Optional containing the product if found
     */
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    /**
     * Add a new product with price validation
     *
     * @param product Product to add
     * @return Added product if price is valid
     * @throws IllegalArgumentException if price exceeds threshold
     */
    public Product addProduct(Product product) {
        if (product.getPrice() > PRICE_THRESHOLD) {
            throw new IllegalArgumentException(
                String.format("Product price %.2f USD exceeds maximum allowed price %.2f USD",
                    product.getPrice(), PRICE_THRESHOLD)
            );
        }
        return productRepository.save(product);
    }

    /**
     * Update an existing product
     *
     * @param product Product to update
     * @return Updated product
     * @throws IllegalArgumentException if product not found or price exceeds threshold
     */
    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }

        // Verify product exists
        if (!productRepository.findById(product.getId()).isPresent()) {
            throw new IllegalArgumentException("Product not found with ID: " + product.getId());
        }

        // Check price threshold for updates
        if (product.getPrice() > PRICE_THRESHOLD) {
            throw new IllegalArgumentException(
                String.format("Product price %.2f USD exceeds maximum allowed price %.2f USD",
                    product.getPrice(), PRICE_THRESHOLD)
            );
        }

        return productRepository.save(product);
    }

    /**
     * Delete a product by its ID
     *
     * @param id Product ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteProduct(Integer id) {
        return productRepository.deleteById(id);
    }

    /**
     * Get products by category
     *
     * @param category Product category to filter by
     * @return List of products in the specified category
     */
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.filterByCategory(category);
    }

    /**
     * Get all products with price below threshold
     *
     * @return List of products with price below threshold
     */
    public List<Product> getProductsBelowThreshold() {
        return productRepository.findAll().stream()
            .filter(product -> product.getPrice() <= PRICE_THRESHOLD)
            .toList();
    }
}