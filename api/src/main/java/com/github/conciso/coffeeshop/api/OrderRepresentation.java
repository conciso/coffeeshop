package com.github.conciso.coffeeshop.api;

import com.github.conciso.coffeeshop.domain.model.Addition;
import com.github.conciso.coffeeshop.domain.model.Drink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepresentation {

  private List<Addition> additions = new ArrayList<>();
  private Drink drink;

  public OrderRepresentation() {}

  public void setDrink(Drink drink) {
    this.drink = Objects.requireNonNull(drink);
  }

  public Drink getDrink() {
    return drink;
  }

  public List<Addition> getAdditions() {
    return additions;
  }

  public void setAdditions(List<Addition> additions) {
    this.additions = new ArrayList<>(Objects.requireNonNull(additions));
  }
}
