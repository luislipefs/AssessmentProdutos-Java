package br.com.at_produtos.Controller;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.CurrencyService;
import br.com.at_produtos.Services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        return productService.getAllProducts(quantity, order, sortBy);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null){
            throw new ResourceNotFoundException();
        }
            return productService.getProductById(id);
    }

    @GetMapping("usd/{id}")
    public Product convertedToDollar(@PathVariable int id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
            }
            return productService.convertToDollar(product);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para dólar", e);
        }
    }

    @GetMapping("/dolarPrice")
    public double getPriceUSD(){
        double dolarPrice = currencyService.getPriceUSD();
        return dolarPrice;
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @PostMapping("/addMultiple")
    public void addProducts(@RequestBody List<Product> productList) {
        productService.addProducts(productList);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.ok("Todos os produtos foram removidos da lista.");
    }
}
