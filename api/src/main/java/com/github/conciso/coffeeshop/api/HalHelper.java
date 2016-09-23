package com.github.conciso.coffeeshop.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.conciso.coffeeshop.infrastructure.ObjectMapperContextResolver;
import com.google.common.base.Throwables;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class HalHelper {

  private static final Set<String> SPECIAL_FIELDS =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList("self", "curies")));

  private HalHelper() {}

  /**
   * Workaround for this bug: https://github.com/HalBuilder/halbuilder-json/pull/20
   */
  public static JsonNode coalesceLinks(Representation representation) {
    ObjectNode json = null;
    try {
      json = new ObjectMapperContextResolver().getContext(representation.getClass())
          .readValue(representation.toString(RepresentationFactory.HAL_JSON), ObjectNode.class);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
    json.get("_links").fields().forEachRemaining(field -> {
      JsonNode value = field.getValue();
      if (!SPECIAL_FIELDS.contains(field.getKey()) && value.isArray() && value.size() <= 1) {
        field.setValue(value.get(0));
      }
    });
    return json;
  }

  public static URI fromResource(UriInfo uriInfo, Class<?> clazz, String method, Object... params) {
    UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
    if (clazz.isAnnotationPresent(Path.class)) {
      uriBuilder.path(clazz);
    }
    if (Arrays.asList(clazz.getDeclaredMethods()).stream()
        .anyMatch(m -> m.getName().equalsIgnoreCase(method) && m.isAnnotationPresent(Path.class))) {
      uriBuilder.path(clazz, method);
    }
    return uriBuilder.build(params);
  }

}
