package com.github.conciso.coffeeshop.api;


import static com.github.conciso.coffeeshop.api.HalLinks.coalesce;

import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.model.OrderStatus;
import com.github.conciso.coffeeshop.domain.service.Orders;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/barista")
public class BaristaResource {

  @Inject
  Orders orders;

  @Inject
  RepresentationFactory representationFactory;

  @Context
  UriInfo uriInfo;

  @Path("/{orderId}")
  @GET
  @Produces(RepresentationFactory.HAL_JSON)
  public Response receiveDrink(@PathParam("orderId") int orderId) {
    Order order = orders.get(orderId).orElseThrow(NotFoundException::new);
    Representation representation = representationFactory.newRepresentation();
    new HalLinks(uriInfo, order).addTo(representation);
    if (!order.isPayed()) {
      return Response.status(Response.Status.PAYMENT_REQUIRED)
          .entity(coalesce(representation)).build();
    } else {
      order.setStatus(OrderStatus.RECEIVED);
      return Response.ok(coalesce(representation)).build();
    }
  }

}
