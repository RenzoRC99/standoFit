package com.standofit.back.shared.domain.criteria;

/**
 * Filter operator for building criteria queries.
 *
 * <p>Supported operators: - EQUAL: exact match (=) - NOT_EQUAL: not equal (!=) - GT: greater than
 * (>) - GTE: greater than or equal (>=) - LT: less than (<) - LTE: less than or equal (<=) - LIKE:
 * contains/like pattern (%) - IN: in a list of values - IS_NULL: is null - IS_NOT_NULL: is not null
 *
 * @author standofit
 * @version 1.0
 */
public enum FilterOperator {
  EQUAL("="),
  NOT_EQUAL("!="),
  GT(">"),
  GTE(">="),
  LT("<"),
  LTE("<="),
  LIKE("like"),
  IN("in"),
  IS_NULL("is_null"),
  IS_NOT_NULL("is_not_null");

  private final String symbol;

  FilterOperator(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return symbol;
  }
}
