package com.github.conciso.coffeeshop.infrastructure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;

import java.io.IOException;

public class MoneySerializer extends StdSerializer<Money> {

  protected MoneySerializer() {
    super(Money.class);
  }

  @Override
  public void serialize(Money value, JsonGenerator jgen, SerializerProvider sp) throws IOException {
    jgen.writeString(value.toString());
  }
}
