package de.conciso.coffeeshop.representation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.conciso.coffeeshop.Addition;
import de.conciso.coffeeshop.Bill;
import de.conciso.coffeeshop.Drink;
import java.util.List;
import org.immutables.value.Value;
import org.joda.money.Money;

@Value.Immutable
@Representation
@JsonDeserialize(as = ImmutableBillRepresentation.class)
@JsonSerialize(as = ImmutableBillRepresentation.class)
public interface BillRepresentation {

  OrderRepresentation bill();
  String cost();
  boolean paid();

  static BillRepresentation from(Bill bill) {
    return ImmutableBillRepresentation.builder()
        .bill(OrderRepresentation.from(bill.getOrder()))
        .cost(bill.getCost().toString())
        .paid(bill.isPayed())
        .build();
  }

}
