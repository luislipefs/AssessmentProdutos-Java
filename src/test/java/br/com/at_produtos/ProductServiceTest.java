package br.com.at_produtos;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.CurrencyService;
import br.com.at_produtos.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProductsEmptyList() {
        List<Product> result = productService.getAllProducts(null, null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetProductById_ProductNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void testConvertToDollar_Exception() {
        Product product = new Product("Product1", 1, 10.0, List.of(5.0, 5.0, 5.0));
        when(currencyService.getPriceUSD()).thenThrow(new RuntimeException("Error fetching exchange rate"));
        assertThrows(ResourceNotFoundException.class, () -> productService.convertToDollar(product));
    }
    @Test
    void testDeleteProduct() {
        Product product = new Product("Product1", 1, 10.0, List.of(5.0, 5.0, 5.0));
        productService.addProduct(product);
        productService.deleteProduct(1);
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void testDeleteAllProducts() {

        productService.addProduct(new Product("Product1", 1, 10.0, List.of(5.0, 5.0, 5.0)));
        productService.addProduct(new Product("Product2", 2, 15.0, List.of(7.0, 7.0, 7.0)));
        productService.deleteAllProducts();
        assertEquals(0, productService.getAllProducts(10, "name", "asc").size());
    }

    @Test
    void testGetProductByIdNotFound() {
        productService.addProducts(java.util.Arrays.asList(
                new Product("Product 1", 1, 15.0, Arrays.asList(3.0)),
                new Product("Product 2", 2, 20.0, Arrays.asList(4.0)),
                new Product("Product 3", 3, 10.0, Arrays.asList(2.0))
        ));

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999));
    }

    @Test
    void testUpdateProduct() {
        productService.deleteAllProducts();
        Product existingProduct = new Product("Product1", 4, 10.0, List.of(5.0, 5.0, 5.0));
        productService.addProduct(existingProduct);
        Product updatedProduct = new Product("UpdatedProduct", 4, 20.0, List.of(10.0, 10.0, 10.0));
        productService.updateProduct(4, updatedProduct);
        Product retrievedProduct = productService.getProductById(4);

        assertEquals("UpdatedProduct", retrievedProduct.getName());
        assertEquals(4, retrievedProduct.getId());
        assertEquals(20.0, retrievedProduct.getPrice(), 0.001);
        assertEquals(List.of(10.0, 10.0, 10.0), retrievedProduct.getSize());
    }

    @Test
    void testAddProductsNullList() {
        assertThrows(NullPointerException.class, () -> productService.addProducts(null));
    }
}

