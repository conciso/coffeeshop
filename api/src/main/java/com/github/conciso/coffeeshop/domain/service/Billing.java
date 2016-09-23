package com.github.conciso.coffeeshop.domain.service;

import com.github.conciso.coffeeshop.domain.model.Bill;
import com.github.conciso.coffeeshop.domain.model.Order;

import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Billing {

  private ConcurrentHashMap<Integer, Bill> billsByOrder = new ConcurrentHashMap<>();

  public Bill bill(Order order) {
    Bill bill = new Bill(order);
    billsByOrder.put(order.getId(), bill);
    return bill;
  }
}
