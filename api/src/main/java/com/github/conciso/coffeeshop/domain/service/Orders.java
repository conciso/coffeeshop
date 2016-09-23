package com.github.conciso.coffeeshop.domain.service;

import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.model.OrderStatus;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Orders {

  private ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<>();

  public void add(Order order) {
    orders.add(Objects.requireNonNull(order));
  }

  public boolean update(Order order) {
    Optional<Order> currentOrder = get(order.getId());
    if (currentOrder.isPresent() && !OrderStatus.PAYED.equals(currentOrder.get().getStatus())) {
      if (orders.remove(currentOrder.get())) {
        orders.add(order);
        return true;
      }
    }
    return false;
  }

  public Optional<Order> get(int orderId) {
    return orders.stream().filter(o -> o.getId() == orderId).findFirst();
  }
}
