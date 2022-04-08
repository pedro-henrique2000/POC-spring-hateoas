package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        product.add(linkTo(methodOn(ProductController.class).findAll()).withRel("products"));

        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            int productId = product.getId();

            product.add(linkTo(methodOn(ProductController.class).getById(productId)).withSelfRel());
        }

        return ResponseEntity.ok(productList);
    }

}
