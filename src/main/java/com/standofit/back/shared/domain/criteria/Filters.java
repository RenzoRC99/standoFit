package com.standofit.back.shared.domain.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Collection of Filter objects — like PHP Filters wrapper.
 *
 * @author standofit
 * @version 1.0
 */
public record Filters(List<Filter> items) {

  public Filters {
    if (items == null) {
      items = List.of();
    } else {
      items = List.copyOf(items);
    }
  }

  public static Filters empty() {
    return new Filters(List.of());
  }

  public static Filters fromValues(List<Map<String, String>> values) {
    if (values == null || values.isEmpty()) {
      return empty();
    }
    return new Filters(values.stream().map(Filter::fromValues).toList());
  }

  public static Filters fromFilters(List<Filter> filters) {
    return new Filters(filters != null ? filters : List.of());
  }

  public Filters add(Filter filter) {
    var list = new ArrayList<>(items);
    list.add(filter);
    return new Filters(list);
  }

  public List<Filter> filters() {
    return items;
  }

  public int count() {
    return items.size();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public String serialize() {
    return items.stream()
        .map(Filter::serialize)
        .reduce("", (acc, s) -> acc + "^" + s);
  }
}