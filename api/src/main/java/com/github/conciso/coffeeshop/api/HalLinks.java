package com.github.conciso.coffeeshop.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.conciso.coffeeshop.domain.model.Order;
import com.github.conciso.coffeeshop.domain.model.OrderStatus;
import com.github.conciso.coffeeshop.infrastructure.ObjectMapperContextResolver;
import com.google.common.base.Throwables;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

class HalLinks {

  private static final Set<String> SPECIAL_FIELDS =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList("self", "curies")));

  private Map<OrderStatus, List<Consumer<Representation>>> linkAdder = new HashMap<>();

  private final UriInfo uriInfo;
  private final Order order;

  public HalLinks(UriInfo uriInfo, Order order) {
    this.uriInfo = Objects.requireNonNull(uriInfo);
    this.order = Objects.requireNonNull(order);
    linkAdder.put(OrderStatus.PLACED, Arrays.asList(
        this::addGetBillUri,
        this::addPaymentUri,
        this::addUpdateUri));
    linkAdder.put(OrderStatus.PAYED, Arrays.asList(
        this::addGetBillUri,
        this::addReceiveDrinkUri));
    linkAdder.put(OrderStatus.RECEIVED, Collections.singletonList(
        this::addGetBillUri));
  }

  /**
   * Workaround for this bug: https://github.com/HalBuilder/halbuilder-json/pull/20
   */
  static JsonNode coalesce(Representation representation) {
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

  static URI fromResource(UriInfo uriInfo, Class<?> clazz, String method, Object... params) {
    UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
    if (clazz.isAnnotationPresent(Path.class)) {
      uriBuilder.path(clazz);
    }
    if (Arrays.stream(clazz.getDeclaredMethods())
        .anyMatch(m -> m.getName().equalsIgnoreCase(method) && m.isAnnotationPresent(Path.class))) {
      uriBuilder.path(clazz, method);
    }
    return uriBuilder.build(params);
  }

  private URI fromResource(Class<?> clazz, String method, Object... params) {
    return fromResource(uriInfo, clazz, method, params);
  }

  private void addReceiveDrinkUri(Representation representation) {
    String receiveDrinkUri =  fromResource(
        BaristaResource.class, "receiveDrink", order.getId()).toString();
    representation.withLink("coffeeshop:receiveDrink", receiveDrinkUri);
  }

  private void addGetBillUri(Representation representation) {
    String receiveDrinkUri =  fromResource(
        CashierResource.class, "getBill", order.getId()).toString();
    representation.withLink("coffeeshop:getBill", receiveDrinkUri);
  }

  private void addPaymentUri(Representation representation) {
    String paymentUri =  fromResource(
        CreditCardTerminalResoure.class, "payWithCc", order.getId()).toString();
    representation.withLink("coffeeshop:payWithCc", paymentUri);
  }

  private void addUpdateUri(Representation representation) {
    String paymentUri =  fromResource(
        CashierResource.class, "updateOrder", order.getId()).toString();
    representation.withLink("coffeeshop:updateOrder", paymentUri);
  }

  public void addTo(Representation representation) {
    linkAdder.getOrDefault(order.getStatus(), Collections.emptyList())
        .forEach(adder -> adder.accept(representation));
  }

}
