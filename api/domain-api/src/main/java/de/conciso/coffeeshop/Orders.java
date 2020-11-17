package de.conciso.coffeeshop;

import java.util.Optional;

public interface Orders {

  void add(Order order);

  boolean update(Order order);

  Optional<Order> get(int orderId);

}
