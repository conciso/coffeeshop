package de.conciso.coffeeshop.representation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.conciso.coffeeshop.Drink;
import java.util.Optional;
import org.immutables.value.Value;
import org.joda.money.Money;

@Value.Immutable
@Representation
@JsonDeserialize(as = ImmutableDrinkRepresentation.class)
@JsonSerialize(as = ImmutableDrinkRepresentation.class)
public interface DrinkRepresentation {
  String name();

  Optional<String> price();

  static DrinkRepresentation from(Drink drink) {
    return ImmutableDrinkRepresentation.builder()
        .name(drink.name())
        .price(drink.getPrice().toString())
        .build();
  }

  static Drink to(DrinkRepresentation drink) {
    return Drink.valueOf(drink.name());
  }

}
