package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepositoryMock productRepositoryMock;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        return ResponseEntity.ok(productRepositoryMock.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productRepositoryMock.findAll());
    }

}
