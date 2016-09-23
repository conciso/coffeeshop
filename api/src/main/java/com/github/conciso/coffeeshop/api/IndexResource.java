package com.github.conciso.coffeeshop.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
  public Response index() {

    URI selfUri = HalHelper.fromResource(uriInfo, IndexResource.class, "index");
    URI placeOrderUri = HalHelper.fromResource(uriInfo, OrderResource.class, "placeOrder");

    JsonNode hal = HalHelper.coalesceLinks(
        representationFactory.newRepresentation(selfUri)
            .withLink("coffeeshop:placeOrder", placeOrderUri));

    return Response.ok(selfUri).entity(hal).build();
  }

}
