package br.com.at_produtos;

import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void testGetAllProductsInvalidQuantity() {
        // Adiciona alguns produtos
        productService.addProducts(Arrays.asList(
                new Product("Product 1", 1, 15.0, Arrays.asList(3.0)),
                new Product("Product 2", 2, 20.0, Arrays.asList(4.0)),
                new Product("Product 3", 3, 10.0, Arrays.asList(2.0))
        ));

        // Tenta obter todos os produtos com uma quantidade negativa
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.getAllProducts(-1, "asc", "id")
        );

        // Verifica se a mensagem da exceção contém a informação esperada
        assertTrue(exception.getMessage().contains("A quantidade deve ser um número positivo"));
    }




    @Test
    void testGetProductByIdNotFound() {
        productService.addProducts(Arrays.asList(
                new Product("Product 1", 1, 15.0, Arrays.asList(3.0)),
                new Product("Product 2", 2, 20.0, Arrays.asList(4.0)),
                new Product("Product 3", 3, 10.0, Arrays.asList(2.0))
        ));

        assertNull(productService.getProductById(999));
    }

    @Test
    void testAddProductsNullList() {
        assertThrows(NullPointerException.class, () -> productService.addProducts(null));
    }

}

