package de.conciso.coffeeshop.integration;

import com.google.common.base.Strings;

import javax.inject.Singleton;

@Singleton
public class CucumberProperties {

  private static String getServiceHost() {
    return System.getProperty("service.host", "localhost");
  }

  public static int getServiceHttpPort() {
    String property = System.getProperty("service.http.port");
    return Strings.isNullOrEmpty(property) ? 8080 : Integer.parseInt(property);
  }

  public static String getBaseUri() {
    return getBaseUri("http", getServiceHttpPort());
  }

  public static String getBaseUri(String protocol, int port) {
    return String.format("%s://%s:%s", protocol, getServiceHost(), port);
  }
}
