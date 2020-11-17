package de.conciso.coffeeshop;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.conciso.coffeeshop.representation.OrderRepresentation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/barista")
@Validated
public class BaristaResource {

  Orders orders;

  public BaristaResource(Orders orders) {
    this.orders = orders;
  }

  @GetMapping(path = "/{orderId}",
      produces = {"application/hal+json", MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<EntityModel<OrderRepresentation>> receiveDrink(@PathVariable("orderId") int orderId) {

    Order order = orders.get(orderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "OrderId not found: " + orderId));

    Link representation = linkTo(methodOn(BaristaResource.class).receiveDrink(orderId)).withSelfRel();

    if (!order.isPaid()) {
      return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
          .body(EntityModel.of(OrderRepresentation.from(order), representation));
    } else {
      order.setStatus(OrderStatus.RECEIVED);
      return ResponseEntity.ok(EntityModel.of(OrderRepresentation.from(order)));
    }
  }
}
