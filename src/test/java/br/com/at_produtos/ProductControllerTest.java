package br.com.at_produtos;

import br.com.at_produtos.Controller.ProductController;
import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.CurrencyService;
import br.com.at_produtos.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
    void testConvertedToDollar() {
        Product product = new Product("Product1", 1, 10.0, List.of(5.0, 5.0, 5.0));
        when(productService.getProductById(1)).thenReturn(product);
        when(productService.convertToDollar(product)).thenReturn(product);
        Product result = productController.convertedToDollar(1);
        assertNotNull(result);
        assertEquals(product, result);
        verify(productService, times(1)).getProductById(1);
        verify(productService, times(1)).convertToDollar(product);
    }

    @Test
    void testConvertedToDollar_ProductNotFound() {
        when(productService.getProductById(1)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> productController.convertedToDollar(1));
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    void testGetPriceUSD() {
        when(currencyService.getPriceUSD()).thenReturn(5.0);
        double result = productController.getPriceUSD();
        assertEquals(5.0, result);
        verify(currencyService, times(1)).getPriceUSD();
    }


    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product1", 1, 10.0, List.of(5.0)));
        productList.add(new Product("Product2", 2, 20.0, List.of(7.0)));
        when(productService.getAllProducts(null, null, null)).thenReturn(productList);
        List<Product> result = productController.getAllProducts(null, null, null);
        assertNotNull(result);
        assertEquals(productList, result);
        verify(productService, times(1)).getAllProducts(null, null, null);
    }

    @Test
    void testGetProductById() {
        Product product = new Product("Product1", 1, 10.0, List.of(5.0,5.0,5.0));
        when(productService.getProductById(1)).thenReturn(product);
        Product result = productController.getProductById(1);
        assertNotNull(result);
        assertEquals(product, result);
        verify(productService, times(2)).getProductById(1);
    }

    @Test
    void testGetProductById_ProductNotFound() {
        when(productService.getProductById(1)).thenReturn(null);
        assertThrows(Exception.class, () -> productController.getProductById(1));
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    void testAddProduct() {
        Product product = new Product("Product1", 1, 10.0, List.of(5.0,5.0,5.0));
        productController.addProduct(product);
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testAddProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product1", 1, 10.0, List.of(5.0,5.0,5.0)));
        productList.add(new Product("Product2", 2, 20.0, List.of(7.0,7.0,7.0)));
        productController.addProducts(productList);
        verify(productService, times(1)).addProducts(productList);
    }

    @Test
    void testUpdateProduct() {
        assertDoesNotThrow(() -> productController.updateProduct(1, new Product()));
    }

    @Test
    void testDeleteProduct() {
        assertDoesNotThrow(() -> productController.deleteProduct(1));
    }

    @Test
    void testDeleteAllProductsEndpoint() {
        productController.deleteAllProducts();
        verify(productService, times(1)).deleteAllProducts();
    }
}
