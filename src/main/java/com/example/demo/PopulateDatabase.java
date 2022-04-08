package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class PopulateDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {

        productRepository.deleteAll();

        List<Product> productList = new ArrayList<>();
        productList.add(Product.builder().name("Iphone").price(2000.00).build());
        productList.add(Product.builder().name("Sofa").price(200.00).build());
        productList.add(Product.builder().name("Shower").price(400.00).build());
        productList.add(Product.builder().name("Xbox").price(1500.00).build());
        productList.add(Product.builder().name("TV").price(6000.00).build());
        productList.add(Product.builder().name("Mouse").price(200.00).build());
        productList.add(Product.builder().name("Keyboard").price(200.00).build());
        productList.add(Product.builder().name("Monitor").price(200.00).build());
        productList.add(Product.builder().name("Chair").price(800.00).build());
        productList.add(Product.builder().name("Book").price(90.00).build());
        productList.add(Product.builder().name("Mousepad").price(20.00).build());

        return args -> {
            log.info("Saving Products");
            productRepository.saveAll(productList);
        };
    }

}
