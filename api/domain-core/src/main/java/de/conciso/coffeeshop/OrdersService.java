package de.conciso.coffeeshop;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrdersService implements Orders {

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
