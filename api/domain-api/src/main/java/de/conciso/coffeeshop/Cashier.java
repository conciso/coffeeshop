package de.conciso.coffeeshop;

import java.util.Objects;
import java.util.Optional;

public interface Cashier {
  int place(Order order);

  boolean update(Order order);

  Optional<Bill> getBill(int orderId);

}
