package com.snackify.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {
  private String productName;
  private double price;
  private int quantity;
  private double totalPrice;
}
