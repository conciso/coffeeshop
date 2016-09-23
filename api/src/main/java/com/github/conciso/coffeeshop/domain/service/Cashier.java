package com.github.conciso.coffeeshop.domain.service;

import com.github.conciso.coffeeshop.domain.model.Bill;
import com.github.conciso.coffeeshop.domain.model.Order;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Cashier {

  @Inject
  private Orders orders;

  private AtomicInteger lastOrderId = new AtomicInteger(0);

  public int place(Order order) {
    Objects.requireNonNull(order);
    int orderId = lastOrderId.incrementAndGet();
    order.setId(orderId);
    orders.add(order);
    return orderId;
  }

  public boolean update(Order order) {
    Objects.requireNonNull(order);
    return orders.update(order);
  }

  public Optional<Bill> getBill(int orderId) {
    Optional<Order> order = orders.get(orderId);
    if (order.isPresent()) {
      return Optional.of(new Bill(order.get()));
    }
    return Optional.empty();
  }
}
