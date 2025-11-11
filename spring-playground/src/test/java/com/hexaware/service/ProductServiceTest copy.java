import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hexaware.model.Product;
import com.hexaware.model.Product.Category;
import com.hexaware.repository.ProductRepository;

package com.hexaware.service;





@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(
            new Product(1, "Test1", 50.0f, Category.ELECTRONICS),
            new Product(2, "Test2", 75.0f, Category.CLOTHING)
        );
        when(productRepository.findAll()).thenReturn(products);
        
        assertEquals(products, productService.getAllProducts());
    }
    
    @Test
    void getProductById_ShouldReturnProduct() {
        Product product = new Product(1, "Test", 50.0f, Category.ELECTRONICS);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        
        assertEquals(Optional.of(product), productService.getProductById(1));
    }
    
    @Test
    void addProduct_WithValidPrice_ShouldSaveProduct() {
        Product product = new Product(1, "Test", 50.0f, Category.ELECTRONICS);
        when(productRepository.save(product)).thenReturn(product);
        
        assertEquals(product, productService.addProduct(product));
    }
    
    @Test
    void addProduct_WithInvalidPrice_ShouldThrowException() {
        Product product = new Product(1, "Test", 150.0f, Category.ELECTRONICS);
        
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
    }
    
    @Test
    void updateProduct_WithValidProduct_ShouldUpdate() {
        Product product = new Product(1, "Test", 50.0f, Category.ELECTRONICS);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        
        assertEquals(product, productService.updateProduct(product));
    }
    
    @Test
    void updateProduct_WithNullId_ShouldThrowException() {
        Product product = new Product(null, "Test", 50.0f, Category.ELECTRONICS);
        
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product));
    }
    
    @Test
    void updateProduct_WithNonexistentProduct_ShouldThrowException() {
        Product product = new Product(1, "Test", 50.0f, Category.ELECTRONICS);
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product));
    }
    
    @Test
    void deleteProduct_ShouldReturnRepositoryResult() {
        when(productRepository.deleteById(1)).thenReturn(true);
        
        assertTrue(productService.deleteProduct(1));
    }
    
    @Test
    void getProductsByCategory_ShouldReturnFilteredProducts() {
        List<Product> products = Arrays.asList(
            new Product(1, "Test1", 50.0f, Category.ELECTRONICS)
        );
        when(productRepository.filterByCategory(Category.ELECTRONICS)).thenReturn(products);
        
        assertEquals(products, productService.getProductsByCategory(Category.ELECTRONICS));
    }
    
    @Test
    void getProductsBelowThreshold_ShouldReturnFilteredProducts() {
        List<Product> products = Arrays.asList(
            new Product(1, "Test1", 50.0f, Category.ELECTRONICS),
            new Product(2, "Test2", 150.0f, Category.CLOTHING)
        );
        when(productRepository.findAll()).thenReturn(products);
        
        List<Product> result = productService.getProductsBelowThreshold();
        assertEquals(1, result.size());
        assertEquals(50.0f, result.get(0).getPrice());
    }
}