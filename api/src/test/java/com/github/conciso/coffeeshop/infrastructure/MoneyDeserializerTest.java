package com.github.conciso.coffeeshop.infrastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.joda.money.CurrencyUnit.EUR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MoneyDeserializerTest {

  ObjectMapper objectMapper;

  @Before
  public void setUp() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Money.class, new MoneyDeserializer());
    objectMapper = new ObjectMapper().registerModule(module);
  }

  @Test
  public void test() throws IOException {
    Money money = objectMapper.readValue("\"EUR 1.23\"", Money.class);
    assertThat(money, is(equalTo(Money.ofMinor(EUR, 123))));
  }

}