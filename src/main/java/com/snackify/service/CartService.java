package com.snackify.service;

import com.snackify.model.Cart;
import com.snackify.model.CartItem;
import com.snackify.model.Product;
import com.snackify.model.User;
import com.snackify.repository.CartItemRepository;
import com.snackify.repository.CartRepository;
import com.snackify.repository.ProductRepository;
import com.snackify.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  public CartService(
      UserRepository userRepository,
      ProductRepository productRepository,
      CartRepository cartRepository,
      CartItemRepository cartItemRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
  }

  public String addToCart(String userEmail, Long productId, int quantity) {
    // 1️⃣ Get the user
    User user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 2️⃣ Get or create the user's cart
    Cart cart =
        cartRepository
            .findByUser(user)
            .orElseGet(
                () -> {
                  Cart newCart = new Cart();
                  newCart.setUser(user);
                  return cartRepository.save(newCart);
                });

    // 3️⃣ Find the product
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    // 4️⃣ Check if item already exists in cart
    Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

    if (existingItem.isPresent()) {
      CartItem item = existingItem.get();
      item.setQuantity(item.getQuantity() + quantity); // increase quantity
      cartItemRepository.save(item);
    } else {
      CartItem newItem = new CartItem();
      newItem.setCart(cart);
      newItem.setProduct(product);
      newItem.setQuantity(quantity);
      cartItemRepository.save(newItem);
    }

    return "Product added to cart!";
  }
}
