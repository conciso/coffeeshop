package de.conciso.coffeeshop;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CashierService implements Cashier {

  private Orders orders;

  private AtomicInteger lastOrderId = new AtomicInteger(0);

  public CashierService(Orders orders) {
    this.orders = orders;
  }

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
