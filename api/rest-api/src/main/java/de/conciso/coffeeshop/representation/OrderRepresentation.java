package de.conciso.coffeeshop.representation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.conciso.coffeeshop.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.immutables.value.Value;

@Value.Immutable
@Representation
@JsonDeserialize(as = ImmutableOrderRepresentation.class)
@JsonSerialize(as = ImmutableOrderRepresentation.class)
public interface OrderRepresentation {

  Optional<Integer> orderId();

  List<AdditionRepresentation> additions();

  DrinkRepresentation drink();

  Optional<String> status();

  static OrderRepresentation from(Order order) {
    return ImmutableOrderRepresentation.builder()
        .drink(DrinkRepresentation.from(order.getDrink()))
        .additions(order.getAdditions().stream()
            .map(addition -> AdditionRepresentation.from(addition))
            .collect(Collectors.toList()))
        .status(order.getStatus().name())
        .orderId(order.getId())
        .build();
  }

  static Order to(int orderId, OrderRepresentation order) {
    Order result = new Order(DrinkRepresentation.to(order.drink()));
    result.setAdditions(AdditionRepresentation.to(order.additions()));
    result.setId(orderId);
    return result;
  }
}
