package com.github.conciso.coffeeshop.infrastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

public class MoneySerializerTest {

  ObjectMapper objectMapper;

  @Before
  public void setUp() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Money.class, new MoneySerializer());
    objectMapper = new ObjectMapper().registerModule(module);
  }

  @Test
  public void test() throws JsonProcessingException {
    String json = objectMapper.writeValueAsString(Money.ofMinor(EUR, 123));
    assertThat(json, is(equalTo("\"EUR 1.23\"")));
  }

}