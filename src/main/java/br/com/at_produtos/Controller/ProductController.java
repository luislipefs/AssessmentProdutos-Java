package br.com.at_produtos.Controller;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import br.com.at_produtos.Services.CurrencyService;
import br.com.at_produtos.Services.ExchangeRateResponse;
import br.com.at_produtos.Services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ProductService productService;


    @GetMapping("{id}/dolar")
    public Product convertToDollar(@PathVariable int id) {
        try {
            // Obtém o produto por ID
            Product product = productService.getProductById(id);

            if (product == null) {
                throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
            }

            // Obtém o valor de "high" do serviço de moeda
            double highValue = Double.parseDouble(currencyService.realToDollar());

            // Converte o preço do produto para dólares usando o valor highValue
            double priceInDollar = product.getPrice() * highValue;

            // Cria uma cópia do produto com o preço em dólares
            Product productInDollar = new Product(
                    product.getName(),
                    product.getId(),
                    priceInDollar,
                    product.getSize()
            );

            return productInDollar;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para dólar", e);
        }
    }

    @GetMapping("/high-value")
    public double getHighValue() {
        return currencyService.getHighValue();
    }


    @GetMapping("/dolar/{id}")
    public ResponseEntity<Product> realDollar(@PathVariable int id) {
        double exchangeRate = getExchangeRate();

        Product product = productService.getProductById(id);

        double priceInDollar = product.getPrice() * exchangeRate;

        product.setPrice(priceInDollar);

        return ResponseEntity.ok(product);
    }
    public double getExchangeRate() {
        String apiUrl = "https://api.invertexto.com/v1/currency/USD_BRL?token=5910|IRxCD4Fz9qGRtEBCPJjV4MytiASHLo1D";
        ResponseEntity<ExchangeRateResponse> responseEntity = new RestTemplate().getForEntity(apiUrl, ExchangeRateResponse.class);

        if (responseEntity.getBody() == null || responseEntity.getBody().getUsdBrl() == null) {
            throw new RuntimeException("Erro ao obter a taxa de câmbio da API externa");
        }
        ExchangeRateResponse exchangeRateResponse = responseEntity.getBody();
        return exchangeRateResponse.getUsdBrl().getPrice();
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        return productService.getAllProducts(quantity, order, sortBy);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
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
}
