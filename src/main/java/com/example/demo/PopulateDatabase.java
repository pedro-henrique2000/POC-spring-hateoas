package com.example.demo;

import com.example.demo.order.Order;
import com.example.demo.order.OrderRepository;
import com.example.demo.order.Status;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
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
    CommandLineRunner initDatabase(ProductRepository productRepository, OrderRepository orderRepository) {

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

        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().id(1L).description("Order Des1").status(Status.CANCELLED).build());
        orders.add(Order.builder().id(2L).description("Order Des2").status(Status.IN_PROGRESS).build());
        orders.add(Order.builder().id(3L).description("Order Des3").status(Status.COMPLETED).build());

        return args -> {
            log.info("Saving Items");
            productRepository.saveAll(productList);
            orderRepository.saveAll(orders);
        };
    }

}
