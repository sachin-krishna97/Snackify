package com.snackify.dto;

import java.util.List;
import lombok.Data;

@Data
public class CartDto {
  private List<CartItemResponseDto> items;
  private double totalPrice;
}
