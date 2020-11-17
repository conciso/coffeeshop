package de.conciso.coffeeshop;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public enum Addition {

  SHOT(Money.ofMajor(CurrencyUnit.USD, 1));
  private Money price;

  Addition(Money price) {
    this.price = price;
  }

  public Money getPrice() {
    return price;
  }
}
