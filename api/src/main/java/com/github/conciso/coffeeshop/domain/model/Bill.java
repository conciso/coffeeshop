package com.github.conciso.coffeeshop.domain.model;

import org.joda.money.Money;

import java.util.Objects;
import java.util.stream.Collectors;

public class Bill {

  private Order order;

  public Bill(Order order) {
    this.order = Objects.requireNonNull(order);
  }

  public Order getOrder() {
    return order;
  }

  public Money getCost() {
    return order.getDrink().getPrice()
        .plus(order.getAdditions().stream()
            .map(Addition::getPrice)
            .collect(Collectors.toList()));
  }

  public boolean isPayed() {
    return order.isPayed();
  }
}
