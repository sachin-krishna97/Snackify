package com.snackify.controller;

import com.snackify.model.product;
import com.snackify.repository.productRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class productController {

  private final productRepository productRepository;

  public productController(productRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public List<product> getAllProducts() {
    return productRepository.findAll();
  }

  @PostMapping
  public product addProduct(@RequestBody product product) {
    return productRepository.save(product);
  }
}
