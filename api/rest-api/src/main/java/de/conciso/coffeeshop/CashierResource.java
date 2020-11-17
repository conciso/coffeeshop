package de.conciso.coffeeshop;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import de.conciso.coffeeshop.representation.AdditionRepresentation;
import de.conciso.coffeeshop.representation.BillRepresentation;
import de.conciso.coffeeshop.representation.DrinkRepresentation;
import de.conciso.coffeeshop.representation.OrderRepresentation;
import java.net.URI;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/cashier")
@Validated
public class CashierResource {

  Cashier cashier;

  public CashierResource(Cashier cashier) {
    this.cashier = cashier;
  }

  @PostMapping(path = "/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = {"application/hal+json", MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<EntityModel<BillRepresentation>> placeOrder(@RequestBody
      OrderRepresentation orderRepresentation) {

    Order order = new Order(DrinkRepresentation.to(orderRepresentation.drink()));
    order.setAdditions(AdditionRepresentation.to(orderRepresentation.additions()));
    int orderId = cashier.place(order);

    Link selfLink = linkTo(methodOn(CashierResource.class).placeOrder(orderRepresentation))
        .withSelfRel();
    Link updateLink = linkTo(
        methodOn(CashierResource.class).updateOrder(orderId, orderRepresentation))
        .withRel("updateOrder");

    return cashier.getBill(orderId)
        .map(bill -> ResponseEntity.created(URI.create("/"))
            .body(
                EntityModel.of(BillRepresentation.from(bill), selfLink, updateLink)))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Could not place order"));
  }

  @PutMapping(path = "/{orderId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = {"application/hal+json", MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<EntityModel<BillRepresentation>> updateOrder(
      @PathVariable("orderId") int orderId, @RequestBody OrderRepresentation order) {

    boolean updated = cashier.update(OrderRepresentation.to(orderId, order));
    Bill bill = cashier.getBill(orderId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderId not found: " + orderId));

    Link representation = linkTo(methodOn(CashierResource.class).updateOrder(orderId, order))
        .withSelfRel();

    if (updated) {
      return ResponseEntity.ok(EntityModel.of(BillRepresentation.from(bill), representation));
    }

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(EntityModel.of(BillRepresentation.from(bill), representation));
  }

  @GetMapping(path = "/{orderId}",
      produces = {"application/hal+json", MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<EntityModel<BillRepresentation>> getBill(
      @PathVariable("orderId") int orderId) {

    Bill bill = cashier.getBill(orderId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderId not found: " + orderId));
    Link representation = linkTo(methodOn(CashierResource.class).getBill(orderId))
        .withSelfRel();

    return ResponseEntity.ok(EntityModel.of(BillRepresentation.from(bill), representation));
  }
}
