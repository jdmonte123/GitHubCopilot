package com.hexaware.controller;

import com.hexaware.model.Product;
import com.hexaware.model.Product.Category;
import com.hexaware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling Product related operations
 */
@RestController
@RequestMapping("/api/hexaware/prod/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor for dependency injection
     *
     * @param productService Service for product operations
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products
     *
     * @return HTTP 200 with list of all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Get a product by ID
     *
     * @param id Product ID
     * @return HTTP 200 with product if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new product
     *
     * @param product Product to create
     * @return HTTP 201 with created product if successful, 400 if price exceeds threshold
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.addProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing product
     *
     * @param id Product ID
     * @param product Product details to update
     * @return HTTP 200 with updated product if successful, 404 if not found, 400 if price exceeds threshold
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try {
            product.setId(id);
            Product updatedProduct = productService.updateProduct(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a product
     *
     * @param id Product ID to delete
     * @return HTTP 204 if deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        boolean deleted = productService.deleteProduct(id);
        return new ResponseEntity<>(deleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    /**
     * Get products by category
     *
     * @param category Product category to filter by
     * @return HTTP 200 with list of products in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Category category) {
        List<Product> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Get products with price below threshold (100 USD)
     *
     * @return HTTP 200 with list of products below price threshold
     */
    @GetMapping("/below-threshold")
    public ResponseEntity<List<Product>> getProductsBelowThreshold() {
        List<Product> products = productService.getProductsBelowThreshold();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}