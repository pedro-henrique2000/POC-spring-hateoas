package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryMock implements ProductRepository{

    private List<Product> productList;

    public Optional<Product> findById(int id) {
        populateList();

        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    public List<Product> findAll() {
        populateList();

        return productList;
    }

    private void populateList() {
        productList = new ArrayList<>();
        productList.add(Product.builder().id(1).name("Iphone").price(120.00).build());
        productList.add(Product.builder().id(2).name("Sofa").price(90.00).build());
        productList.add(Product.builder().id(3).name("Mesa").price(80.00).build());
    }
}
