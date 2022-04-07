package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@AllArgsConstructor
public class Product extends RepresentationModel<Product> {

    private int id;
    private String name;
    private double price;

}
