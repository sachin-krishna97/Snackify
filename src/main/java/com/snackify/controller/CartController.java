package com.snackify.controller;

import com.snackify.dto.CartItemDto;
import com.snackify.security.JwtService;
import com.snackify.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

  private final CartService cartService;
  private final JwtService jwtService;

  public CartController(CartService cartService, JwtService jwtService) {
    this.cartService = cartService;
    this.jwtService = jwtService;
  }

  @PostMapping("/add")
  public ResponseEntity<String> addToCart(
      @RequestBody CartItemDto request, @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.substring(7); // Remove "Bearer " prefix
    String userEmail = jwtService.extractUsername(token);

    String response =
        cartService.addToCart(userEmail, request.getProductId(), request.getQuantity());

    return ResponseEntity.ok(response);
  }
}
