package com.snackify.repository;

import com.snackify.model.product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepository extends JpaRepository<product, Long> {}
