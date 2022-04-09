package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> getById(@PathVariable int id) {
        log.info("Searching Product With Id {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return productModelAssembler.toModel(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Product>> findAll() {
        log.info("Searching All Products");

        List<Product> products = productRepository.findAll();

        List<EntityModel<Product>> productList = products.stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productList,
                linkTo(methodOn(ProductController.class).findAll()).withSelfRel()
        );
    }

    // ex
    @GetMapping("/paginated")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Product>> paginated(Pageable pageable) {
        log.info("Pageable {}", pageable);

        Page<Product> productPage = productRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(productPage, productModelAssembler);
    }

}
