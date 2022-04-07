package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepositoryMock {

    private final List<Product> productList;

    public ProductRepositoryMock() {
        this.productList = new ArrayList<>();
        productList.add(Product.builder().id(1).name("Iphone").price(120.00).build());
        productList.add(Product.builder().id(2).name("Sofa").price(90.00).build());
        productList.add(Product.builder().id(3).name("Mesa").price(80.00).build());
    }

    public Product findById(int id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Product> findAll() {
        return productList;
    }
}
