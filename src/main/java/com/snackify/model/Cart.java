package com.snackify.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // One cart belongs to one user
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  // One cart can have many items
  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> items = new ArrayList<>();

  public void setUser(User user) {}
}
