package com.snackify.controller;

import com.snackify.model.Category;
import com.snackify.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  public Category addCategory(@RequestBody Category category) {
    return categoryRepository.save(category);
  }

  @GetMapping("/view")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Category> viewCategories() {
    return categoryRepository.findAll();
  }
}
