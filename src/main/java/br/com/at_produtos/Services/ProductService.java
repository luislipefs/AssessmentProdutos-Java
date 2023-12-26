package br.com.at_produtos.Services;

import br.com.at_produtos.Exception.ResourceNotFoundException;
import br.com.at_produtos.Model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private CurrencyService currencyService;

    private static int nextId = 1;
    private List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts(Integer quantity, String order, String sortBy) {
        logger.info("Obtendo todos os produtos");
        List<Product> result = new ArrayList<>(products);
        if(result.isEmpty()){
            return ResponseEntity.badRequest().body(result).getBody();
        }

        if (quantity != null && quantity > 0) {
            logger.info("Obtendo listagem com: {} produtos", quantity);
            result = result.stream().limit(quantity).collect(Collectors.toList());
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            if ("id".equalsIgnoreCase(sortBy)) {
                result.sort(Comparator.comparingInt(Product::getId));
            } else if ("name".equalsIgnoreCase(sortBy)) {
                result.sort(Comparator.comparing(Product::getName));
            }
        }

        if (order != null && !order.isEmpty()) {
            if ("desc".equalsIgnoreCase(order)) {
                Collections.reverse(result);
            } else if (!"asc".equalsIgnoreCase(order)) {}
            logger.info("Obtendo todos os produtos ordenado por {}, em ordem {}", sortBy, order);
        }
        return ResponseEntity.ok(result).getBody();
    }

    public Product getProductById(int id) {
        logger.info("Obtendo produto por ID: {}", id);
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new ResourceNotFoundException("Produto não encontrado com o ID: " + id);
    }

    public Product convertToDollar(Product product) {
        try {
            var dollar = currencyService.getPriceUSD();
            double priceInDollar = product.getPrice() / dollar;
            BigDecimal priceInDollarDecimal = BigDecimal.valueOf(priceInDollar)
                    .setScale(2, RoundingMode.HALF_EVEN);

            Product productInDollar = new Product(
                    product.getName(),
                    product.getId(),
                    priceInDollarDecimal.doubleValue(),
                    product.getSize()
            );
            return productInDollar;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao converter para dólar" + e);
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setId(nextId++);
        logger.info("Adicionando um novo produto: {}", product.toString());
    }

    public void addProducts(List<Product> productList) {
        productList.forEach(product -> {
            product.setId(nextId++);
            try{
            products.add(product);
            logger.info("Adicionando um novo produto: {}", product.toString());
            }catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    public void updateProduct(int id, Product updatedProduct) {
        logger.info("Atualizando produto por ID {}: {}", id, updatedProduct.toString());
        for (int i = 0; i < products.size(); i++) {
            Product existingProduct = products.get(i);
            if (existingProduct.getId() == id) {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setSize(updatedProduct.getSize());
                break;
            }
        }
    }


    public void deleteProduct(int id) {
        logger.info("Excluindo produto por ID: {}", id);
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                break;
            }
        }
    }

    public void deleteAllProducts() {
        products.clear();
        logger.info("Todos os produtos foram removidos da lista.");
    }
}

