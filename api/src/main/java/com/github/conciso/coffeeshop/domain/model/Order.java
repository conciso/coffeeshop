package com.github.conciso.coffeeshop.domain.model;

import java.util.Objects;

public class Order {

  private Integer id;
  private Drink drink;

  private Order() {
  }

  private Order(Drink drink) {
    this.drink = Objects.requireNonNull(drink);
  }

  public Integer getId() {
    return id;
  }

  public Drink getDrink() {
    return drink;
  }

  public void setId(int id) {
    this.id = id;
  }
}
