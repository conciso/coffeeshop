package com.github.conciso.coffeeshop.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import java.net.MalformedURLException;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
public class RepresentationFactoryProducer {

  @Context
  Providers providers;

  @Context
  ServletContext servletContext;

  @Produces
  public RepresentationFactory createRepresentationFactory() throws MalformedURLException {
    ContextResolver<ObjectMapper> resolver = providers.getContextResolver(
        ObjectMapper.class, MediaType.valueOf(RepresentationFactory.HAL_JSON));
    ObjectMapper objectMapper = resolver.getContext(Representation.class);
    String docsUri = Joiner.on("/").join(servletContext.getContextPath(), "docs", "#{rel}");
    return new JsonRepresentationFactory(objectMapper)
        .withFlag(RepresentationFactory.COALESCE_LINKS)
        .withNamespace("coffeeshop", docsUri);
  }

}
