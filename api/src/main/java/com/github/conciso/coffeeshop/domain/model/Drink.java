package com.github.conciso.coffeeshop.domain.model;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public enum Drink {

  LATTE(Money.ofMinor(CurrencyUnit.USD, 299)),
  MOCHA(Money.ofMinor(CurrencyUnit.USD, 345)),
  FRESHLY_BREWED_COFFEE(Money.ofMinor(CurrencyUnit.USD, 185));

  private Money price;

  Drink(Money price) {
    this.price = price;
  }

  public Money getPrice() {
    return price;
  }
}
