package com.example.demo;

import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

@Builder
public class Product extends RepresentationModel<Product> {

    private int id;
    private String name;
    private double price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
