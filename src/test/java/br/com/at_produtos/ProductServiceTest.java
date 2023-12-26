package br.com.at_produtos;
import static org.junit.jupiter.api.Assertions.*;

import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        productService.addProducts(createSampleProductList());
    }

    @Test
    void testGetAllProducts() {
        List<Product> result = productService.getAllProducts(null, null, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllProductsInvalidQuantity() {
        assertThrows(RuntimeException.class, () -> productService.getAllProducts(-1, null, null));
    }

    @Test
    void testGetProductById() {
        Product product = productService.getProductById(1);
        assertNotNull(product);
        assertEquals(1, product.getId());
    }

    @Test
    void testGetProductByIdNonexistent() {
        assertNull(productService.getProductById(999));
    }

    @Test
    void testAddProduct() {
        Product newProduct = Product.builder()
                .id(99)
                .name("New Product")
                .price(10.0)
                .size(Collections.singletonList(5.0))
                .build();
        productService.addProduct(newProduct);
        assertEquals(99, productService.getProductById(99).getId());
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = Product.builder()
                .id(1)
                .name("Updated Product")
                .price(20.0)
                .size(Collections.singletonList(2.0))
                .build();
        productService.updateProduct(1, updatedProduct);
        assertEquals("Updated Product", productService.getProductById(1).getName());
    }

    @Test
    void testDeleteProduct() {
        productService.deleteProduct(1);
        assertNull(productService.getProductById(1));
    }

    // Add more tests for other methods as needed

    private List<Product> createSampleProductList() {
        return List.of(
                Product.builder().id(1).name("Product 1").price(15.0).size(Collections.singletonList(3.0)).build(),
                Product.builder().id(2).name("Product 2").price(20.0).size(Collections.singletonList(4.0)).build(),
                Product.builder().id(3).name("Product 3").price(10.0).size(Collections.singletonList(2.0)).build()
        );
    }
}

