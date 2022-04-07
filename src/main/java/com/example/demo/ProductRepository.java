package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(int id);
    List<Product> findAll();
}
