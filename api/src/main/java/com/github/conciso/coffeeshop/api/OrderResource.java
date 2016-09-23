package com.github.conciso.coffeeshop.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.service.Billing;
import com.github.conciso.coffeeshop.domain.service.Cashier;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/order")
public class OrderResource {

  @Inject
  Cashier cashier;

  @Inject
  Billing billing;

  @Inject
  RepresentationFactory representationFactory;

  @Context
  UriInfo uriInfo;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(RepresentationFactory.HAL_JSON)
  public Response placeOrder(Order order) {

    int orderId = cashier.place(order);

    URI selfUri = HalHelper.fromResource(
        uriInfo, OrderResource.class, "get", orderId);
    String paymentUri =  HalHelper.fromResource(
        uriInfo, PaymentResource.class, "payOrder", orderId).toString();

    JsonNode hal =  HalHelper.coalesceLinks(
        representationFactory.newRepresentation(selfUri)
            .withBean(billing.bill(order))
            .withLink("coffeeshop:payment", paymentUri));

    return Response.created(selfUri).entity(hal).build();
  }


  @Path("/order/{orderId}")
  @GET
  @Produces(RepresentationFactory.HAL_JSON)
  public Response get(@PathParam("orderId") int orderId) {
    return Response.ok().build();
  }
}
