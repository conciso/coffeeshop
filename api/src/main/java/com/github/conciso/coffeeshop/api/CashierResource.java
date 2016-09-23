package com.github.conciso.coffeeshop.api;


import static com.github.conciso.coffeeshop.api.HalLinks.coalesce;
import static com.github.conciso.coffeeshop.api.HalLinks.fromResource;

import com.github.conciso.coffeeshop.domain.model.Bill;
import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.service.Cashier;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/cashier")
public class CashierResource {

  @Inject
  Cashier cashier;

  @Inject
  RepresentationFactory representationFactory;

  @Context
  UriInfo uriInfo;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(RepresentationFactory.HAL_JSON)
  public Response placeOrder(OrderRepresentation orderRepresentation) {

    Order order = new Order(orderRepresentation.getDrink());
    order.setAdditions(orderRepresentation.getAdditions());
    int orderId = cashier.place(order);

    URI selfUri = fromResource(uriInfo, CashierResource.class, "getBill", orderId);

    Representation representation = representationFactory.newRepresentation(selfUri);
    cashier.getBill(orderId)
        .ifPresent(bill -> representation.withBean(new BillRepresentation(bill)));
    new HalLinks(uriInfo, order).addTo(representation);
    return Response.created(selfUri).entity(coalesce(representation)).build();
  }

  @Path("/{orderId}")
  @PUT
  @Produces(RepresentationFactory.HAL_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateOrder(@PathParam("orderId") int orderId, Order order) {
    order.setId(orderId);
    boolean updated = cashier.update(order);
    Bill bill = cashier.getBill(orderId).orElseThrow(NotFoundException::new);
    URI selfUri = fromResource(uriInfo, CashierResource.class, "updateOrder", orderId);
    Representation representation = representationFactory.newRepresentation(selfUri)
      .withBean(new BillRepresentation(bill));
    new HalLinks(uriInfo, bill.getOrder()).addTo(representation);
    if (updated) {
      return Response.ok(coalesce(representation)).build();
    }
    return Response.status(Response.Status.CONFLICT).entity(coalesce(representation)).build();
  }

  @Path("/{orderId}")
  @GET
  @Produces(RepresentationFactory.HAL_JSON)
  public Response getBill(@PathParam("orderId") int orderId) {
    URI selfUri = fromResource(uriInfo, CashierResource.class, "getBill", orderId);

    Bill bill = cashier.getBill(orderId).orElseThrow(NotFoundException::new);

    Representation representation = representationFactory.newRepresentation(selfUri)
        .withBean(new BillRepresentation(bill));
    new HalLinks(uriInfo, bill.getOrder()).addTo(representation);
    return Response.ok(selfUri).entity(coalesce(representation)).build();
  }

}
