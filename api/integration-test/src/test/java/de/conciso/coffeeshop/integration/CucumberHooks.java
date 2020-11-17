package de.conciso.coffeeshop.integration;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.parsing.Parser;
import javax.inject.Inject;

public class CucumberHooks {


  @Inject
  private ObjectMapper objectMapper;

  @Before(order = 100)
  public void configureRestAssured() {
    RestAssured.defaultParser = Parser.JSON;
    RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
        objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper))
        .sslConfig(new SSLConfig().relaxedHTTPSValidation().allowAllHostnames());
  }

  @Before(order = 200)
  public void cleanDatabase() {
  }
}
