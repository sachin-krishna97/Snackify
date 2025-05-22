package com.snackify.dto;

import lombok.Data;

@Data
public class CartItemDto {
  private Long productId; // Which product user wants to add
  private int quantity; // How many of that product
}
