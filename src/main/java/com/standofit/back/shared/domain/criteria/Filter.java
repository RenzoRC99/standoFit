package com.standofit.back.shared.domain.criteria;

import java.util.Map;

/**
 * Represents a single filter condition for criteria queries.
 *
 * @param field the field name to filter on
 * @param operator the filter operator
 * @param value the value to compare against
 * @author standofit
 * @version 1.0
 */
public record Filter(FilterField field, FilterOperator operator, FilterValue value) {

  public static Filter fromValues(Map<String, String> values) {
    return new Filter(
        new FilterField(values.get("field")),
        FilterOperator.fromString(values.get("operator")),
        new FilterValue(values.get("value"))
    );
  }

  public static Filter fromValues(String field, String operator, String value) {
    return new Filter(
        new FilterField(field),
        FilterOperator.fromString(operator),
        new FilterValue(value)
    );
  }

  public static Filter equal(String field, Object value) {
    return new Filter(new FilterField(field), FilterOperator.EQUAL, new FilterValue(value.toString()));
  }

  public static Filter notEqual(String field, Object value) {
    return new Filter(new FilterField(field), FilterOperator.NOT_EQUAL, new FilterValue(value.toString()));
  }

  public static Filter like(String field, String value) {
    return new Filter(new FilterField(field), FilterOperator.LIKE, new FilterValue(value));
  }

  public static Filter gt(String field, Comparable value) {
    return new Filter(new FilterField(field), FilterOperator.GT, new FilterValue(value.toString()));
  }

  public static Filter lt(String field, Comparable value) {
    return new Filter(new FilterField(field), FilterOperator.LT, new FilterValue(value.toString()));
  }

  public static Filter gte(String field, Comparable value) {
    return new Filter(new FilterField(field), FilterOperator.GTE, new FilterValue(value.toString()));
  }

  public static Filter lte(String field, Comparable value) {
    return new Filter(new FilterField(field), FilterOperator.LTE, new FilterValue(value.toString()));
  }

  public static Filter in(String field, Object value) {
    return new Filter(new FilterField(field), FilterOperator.IN, new FilterValue(value.toString()));
  }

  public static Filter isNull(String field) {
    return new Filter(new FilterField(field), FilterOperator.IS_NULL, new FilterValue(""));
  }

  public static Filter isNotNull(String field) {
    return new Filter(new FilterField(field), FilterOperator.IS_NOT_NULL, new FilterValue(""));
  }

  public String fieldName() {
    return field.value();
  }

  public Object rawValue() {
    return value.value();
  }

  public String serialize() {
    return field.value() + "." + operator.symbol() + "." + value.value();
  }
}