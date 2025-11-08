package com.hexaware.service;

import com.hexaware.model.Product;
import com.hexaware.model.Product.Category;
import com.hexaware.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Product product;

    @Mock
    private Product existingProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_returnsListFromRepository() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        ProductService svc = new ProductService(productRepository);

        List<Product> result = svc.getAllProducts();

        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_returnsOptionalFromRepository() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        ProductService svc = new ProductService(productRepository);

        Optional<Product> opt = svc.getProductById(1);

        assertTrue(opt.isPresent());
        assertSame(product, opt.get());
        verify(productRepository).findById(1);
    }

    @Test
    void addProduct_savesWhenPriceWithinThreshold() {
        when(product.getPrice()).thenReturn(50.0f);
        when(productRepository.save(product)).thenReturn(product);
        ProductService svc = new ProductService(productRepository);

        Product saved = svc.addProduct(product);

        assertSame(product, saved);
        verify(productRepository).save(product);
    }

    @Test
    void addProduct_throwsWhenPriceExceedsThreshold() {
        when(product.getPrice()).thenReturn(150.0f);
        ProductService svc = new ProductService(productRepository);

        assertThrows(IllegalArgumentException.class, () -> svc.addProduct(product));
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateProduct_throwsWhenIdNull() {
        when(product.getId()).thenReturn(null);
        ProductService svc = new ProductService(productRepository);

        assertThrows(IllegalArgumentException.class, () -> svc.updateProduct(product));
        verify(productRepository, never()).findById(any());
    }

    @Test
    void updateProduct_throwsWhenNotFound() {
        when(product.getId()).thenReturn(1);
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        ProductService svc = new ProductService(productRepository);

        assertThrows(IllegalArgumentException.class, () -> svc.updateProduct(product));
        verify(productRepository).findById(1);
    }

    @Test
    void updateProduct_throwsWhenPriceTooHigh() {
        when(product.getId()).thenReturn(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(product.getPrice()).thenReturn(200.0f);
        ProductService svc = new ProductService(productRepository);

        assertThrows(IllegalArgumentException.class, () -> svc.updateProduct(product));
        verify(productRepository).findById(1);
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateProduct_savesWhenValid() {
        when(product.getId()).thenReturn(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(product.getPrice()).thenReturn(80.0f);
        when(productRepository.save(product)).thenReturn(product);
        ProductService svc = new ProductService(productRepository);

        Product updated = svc.updateProduct(product);

        assertSame(product, updated);
        verify(productRepository).findById(1);
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_delegatesToRepository() {
        when(productRepository.deleteById(1)).thenReturn(true);
        when(productRepository.deleteById(2)).thenReturn(false);
        ProductService svc = new ProductService(productRepository);

        assertTrue(svc.deleteProduct(1));
        assertFalse(svc.deleteProduct(2));
        verify(productRepository).deleteById(1);
        verify(productRepository).deleteById(2);
    }

    @Test
    void getProductsByCategory_returnsFilteredList() {
        // stub repository to return list when called with null category
        when(productRepository.filterByCategory(null)).thenReturn(List.of(product));
        ProductService svc = new ProductService(productRepository);

        List<Product> list = svc.getProductsByCategory(null);

        assertEquals(1, list.size());
        verify(productRepository).filterByCategory(null);
    }

    @Test
    void getProductsBelowThreshold_filtersByPrice() {
        Product p1 = mock(Product.class);
        Product p2 = mock(Product.class);
        Product p3 = mock(Product.class);
        when(p1.getPrice()).thenReturn(50.0f);
        when(p2.getPrice()).thenReturn(100.0f);
        when(p3.getPrice()).thenReturn(150.0f);
        when(productRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        ProductService svc = new ProductService(productRepository);

        List<Product> result = svc.getProductsBelowThreshold();

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
        assertFalse(result.contains(p3));
        verify(productRepository).findAll();
    }
}