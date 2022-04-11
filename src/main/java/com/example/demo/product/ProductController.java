package com.example.demo.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<Product>> saveProduct(@RequestBody ProductDTO productDTO) {
        Product product = Product
                .builder()
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .build();

        EntityModel<Product> productEntityModel = productModelAssembler.toModel(productRepository.save(product));

        return ResponseEntity
                .created(productEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) // Location HEADER
                .body(productEntityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable int id) {
        Product product = Product
                .builder()
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .build();

        Product updatedProduct = productRepository.findById(id)
                .map(foundProduct -> {
                    foundProduct.setName(product.getName());
                    foundProduct.setPrice(product.getPrice());
                    return productRepository.save(foundProduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });

        EntityModel<Product> entityModel = productModelAssembler.toModel(updatedProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        productRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
