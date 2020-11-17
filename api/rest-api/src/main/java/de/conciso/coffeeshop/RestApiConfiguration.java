package de.conciso.coffeeshop;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfiguration {

  @Bean
  public ObjectMapper createObjectMapper() {
    return new ObjectMapper()
        .findAndRegisterModules()
        .registerModule(new JavaTimeModule()
            .addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME)))
        .registerModule(new Jdk8Module())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setSerializationInclusion(Include.NON_ABSENT)
        .setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY));
  }

  @Bean
  public OpenAPI customOpenAPI(@Value("0.0.1") String appVersion) {
    return new OpenAPI()
        .addServersItem(new Server().url("http://localhost:8080").description("development server"))
        .info(new Info().title("Parameter Configurator").version(appVersion)
            .license(new License().name("Copyright 2020 Conciso GmbH")
                .url("https://conciso.de/")));
  }

}
