package com.snackify.repository;

import com.snackify.model.Cart;
import com.snackify.model.CartItem;
import com.snackify.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
