package com.github.conciso.coffeeshop.domain.service;

import com.github.conciso.coffeeshop.domain.model.Order;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Cashier {

  private AtomicInteger lastOrderId = new AtomicInteger(0);
  private ConcurrentHashMap<Integer, Order> ordersById = new ConcurrentHashMap<>();

  public int place(Order order) {
    int orderId = lastOrderId.incrementAndGet();
    order.setId(orderId);
    ordersById.put(orderId, Objects.requireNonNull(order));
    return orderId;
  }
}
