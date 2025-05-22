package com.snackify.repository;

import com.snackify.model.Cart;
import com.snackify.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUser(User user);

  Optional<Cart> findByUserId(Long userId);
}
