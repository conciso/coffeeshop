package com.github.conciso.coffeeshop.api;


import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payment/order")
public class PaymentResource {

  @Path("/{orderId}")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(RepresentationFactory.HAL_JSON)
  public Response payOrder(@PathParam("orderId") int orderId) {
    return Response.ok().build();
  }

}
