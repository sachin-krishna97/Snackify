package com.snackify.controller;

import com.snackify.model.Product;
import com.snackify.repository.productRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "bearerAuth")
public class productController {

  private final productRepository productRepository;

  public productController(productRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/view")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Product> getAllProducts() {
    return productRepository.findAll(); // both USER and ADMIN can see products
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  public Product addProduct(@RequestBody Product product) {
    return productRepository.save(product); // only ADMIN can add products
  }
}
