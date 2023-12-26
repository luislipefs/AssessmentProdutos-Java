package br.com.at_produtos.Model;

import lombok.*;

import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Builder@Getter@Setter
public class Product {
    private String name;
    private int id;
    private Double price;
    private List<Double> size;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", size=" + size +
                '}';
    }
}
