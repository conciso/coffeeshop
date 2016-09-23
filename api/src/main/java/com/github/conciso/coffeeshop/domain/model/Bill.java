package com.github.conciso.coffeeshop.domain.model;

import org.joda.money.Money;

import java.util.Objects;

public class Bill {

  private Order order;

  public Bill(Order order) {
    this.order = Objects.requireNonNull(order);
  }

  public Money getCost() {
    return order.getDrink().getPrice();
  }

  public Drink getDrink() {
    return order.getDrink();
  }
}
