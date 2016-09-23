package com.github.conciso.coffeeshop.api;

import static com.github.conciso.coffeeshop.api.HalLinks.coalesce;
import static com.github.conciso.coffeeshop.api.HalLinks.fromResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class IndexResource {

  @Context
  UriInfo uriInfo;

  @Inject
  RepresentationFactory representationFactory;

  @GET
  @Produces(RepresentationFactory.HAL_JSON)
  public Response index() {

    URI selfUri = fromResource(uriInfo, IndexResource.class, "index");
    URI placeOrderUri = fromResource(uriInfo, CashierResource.class, "placeOrder");

    JsonNode hal = coalesce(
        representationFactory.newRepresentation(selfUri)
            .withLink("coffeeshop:placeOrder", placeOrderUri));

    return Response.ok(selfUri).entity(hal).build();
  }

}
