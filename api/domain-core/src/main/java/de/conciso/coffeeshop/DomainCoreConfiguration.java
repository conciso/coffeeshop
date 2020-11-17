package de.conciso.coffeeshop;

import java.time.Clock;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@org.springframework.context.annotation.Configuration
public class DomainCoreConfiguration {

  @Bean
  @Scope("singleton")
  public Orders createOrdersService() {
    return new OrdersService();
  }

  @Bean
  @Scope("singleton")
  public Cashier createCashierService(Orders orders)
  {
    return new CashierService(orders);
  }

}
