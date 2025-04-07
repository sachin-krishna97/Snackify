package com.snackify.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private double price;
  private String category; // e.g., "Sweet", "Savory"
  private String imageUrl; // link to snack image
}
