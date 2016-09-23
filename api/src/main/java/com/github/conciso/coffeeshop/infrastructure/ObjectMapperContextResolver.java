package com.github.conciso.coffeeshop.infrastructure;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.Money;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

  private ObjectMapper objectMapper;

  public ObjectMapperContextResolver() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Money.class, new MoneyDeserializer());
    module.addSerializer(Money.class, new MoneySerializer());
     objectMapper = new ObjectMapper().registerModule(module);
  }

  @Override
  public ObjectMapper getContext(Class<?> aClass) {
    return objectMapper;
  }
}
