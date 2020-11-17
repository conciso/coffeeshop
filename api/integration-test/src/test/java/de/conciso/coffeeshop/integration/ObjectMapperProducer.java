package de.conciso.coffeeshop.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.enterprise.inject.Produces;

public class ObjectMapperProducer {

  public static final ObjectMapper INSTANCE = new ObjectMapper()
      .findAndRegisterModules()
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
      .setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY))
      .registerModule(new JavaTimeModule()
          .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
          .addSerializer(LocalDateTime.class,
              new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
          .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME)))
      .registerModule(new Jdk8Module());

  @Produces
  public ObjectMapper produceObjectMapper() {
    return INSTANCE;
  }

}
