package com.snackify.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private double price;
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "category_id") // this will create a foreign key
  private Category category;
}
