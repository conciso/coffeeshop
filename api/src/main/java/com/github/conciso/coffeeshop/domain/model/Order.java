package com.github.conciso.coffeeshop.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

  private int id;
  private Drink drink;
  private List<Addition> additions;
  private OrderStatus status;

  private Order() {
    this.status = OrderStatus.PLACED;
    additions = new ArrayList<>();
  }

  public Order(Drink drink) {
    this();
    this.drink = Objects.requireNonNull(drink);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Drink getDrink() {
    return drink;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = Objects.requireNonNull(status);
  }

  public List<Addition> getAdditions() {
    return new ArrayList<>(additions);
  }

  public void setAdditions(List<Addition> additions) {
    this.additions = new ArrayList<>(Objects.requireNonNull(additions));
  }

  public boolean isPayed() {
    return status != OrderStatus.PLACED;
  }
}
