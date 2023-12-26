package br.com.at_produtos;

import br.com.at_produtos.Controller.ProductController;
import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.CurrencyService;
import br.com.at_produtos.Services.ExchangeRateResponse;
import br.com.at_produtos.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testConvertToDollarWithCurrencyServiceException() {
        when(productService.getProductById(anyInt())).thenReturn(new Product());
        when(currencyService.realToDollar()).thenThrow(new RuntimeException("Test exception"));
        assertThrows(RuntimeException.class, () -> productController.convertToDollar(1));
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts(any(), any(), any())).thenReturn(Collections.emptyList());
        assertNotNull(productController.getAllProducts(null, null, null));
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(anyInt())).thenReturn(new Product());
        assertNotNull(productController.getProductById(1));
    }

    @Test
    void testAddProduct() {
        assertDoesNotThrow(() -> productController.addProduct(new Product()));
    }

    @Test
    void testAddProducts() {
        assertDoesNotThrow(() -> productController.addProducts(Collections.singletonList(new Product())));
    }

    @Test
    void testUpdateProduct() {
        assertDoesNotThrow(() -> productController.updateProduct(1, new Product()));
    }

    @Test
    void testDeleteProduct() {
        assertDoesNotThrow(() -> productController.deleteProduct(1));
    }
}
