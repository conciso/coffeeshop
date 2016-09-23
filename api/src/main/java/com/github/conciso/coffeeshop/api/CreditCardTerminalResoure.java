package com.github.conciso.coffeeshop.api;


import static com.github.conciso.coffeeshop.api.HalLinks.coalesce;
import static com.github.conciso.coffeeshop.api.HalLinks.fromResource;

import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.model.OrderStatus;
import com.github.conciso.coffeeshop.domain.service.Orders;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/ccterminal/{orderId}")
public class CreditCardTerminalResoure {

  @Context
  UriInfo uriInfo;

  @Inject
  RepresentationFactory representationFactory;

  @Inject
  Orders orders;

  @PUT
  @Produces(RepresentationFactory.HAL_JSON)
  public Response payWithCc(@PathParam("orderId") int orderId) {
    Order order = orders.get(orderId).orElseThrow(NotFoundException::new);
    order.setStatus(OrderStatus.PAYED);

    URI selfUri = fromResource(uriInfo, CreditCardTerminalResoure.class, "payWithCc", orderId);

    Representation representation = representationFactory.newRepresentation(selfUri);
    new HalLinks(uriInfo, order).addTo(representation);
    return Response.ok(selfUri).entity(coalesce(representation)).build();
  }

}
