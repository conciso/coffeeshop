package com.github.conciso.coffeeshop.api;

import com.github.conciso.coffeeshop.domain.model.Addition;
import com.github.conciso.coffeeshop.domain.model.Bill;
import com.github.conciso.coffeeshop.domain.model.Drink;
import org.joda.money.Money;

import java.util.List;

public class BillRepresentation {

  private Bill bill;

  BillRepresentation(Bill bill) {
    this.bill = bill;
  }

  public Money getCost() {
    return bill.getCost();
  }

  public Drink getDrink() {
    return bill.getOrder().getDrink();
  }

  public List<Addition>  getAdditions() {
    return bill.getOrder().getAdditions();
  }

}
