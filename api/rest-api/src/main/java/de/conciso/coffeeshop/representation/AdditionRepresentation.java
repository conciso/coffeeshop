package de.conciso.coffeeshop.representation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.conciso.coffeeshop.Addition;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.immutables.value.Value;
import org.joda.money.Money;

@Value.Immutable
@Representation
@JsonDeserialize(as = ImmutableAdditionRepresentation.class)
@JsonSerialize(as = ImmutableAdditionRepresentation.class)
public interface AdditionRepresentation {


  String name();

  Optional<String> price();

  static Addition to(AdditionRepresentation addition) {
    return Addition.valueOf(addition.name());
  }

  static AdditionRepresentation from(Addition addition) {
    return ImmutableAdditionRepresentation.builder()
        .name(addition.name())
        .price(addition.getPrice().toString())
        .build();
  }

  static List<Addition> to(List<AdditionRepresentation> additions) {
    return additions.stream().map(AdditionRepresentation::to).collect(Collectors.toList());
  }
}
