package com.standofit.back.shared.domain.criteria;

/**
 * Order direction for sorting results.
 *
 * @author standofit
 * @version 1.0
 */
public enum Order {
  ASC,
  DESC;

  public static Order fromValues(String orderBy, String order) {
    if (orderBy == null || orderBy.isBlank()) {
      return null;
    }
    return "ASC".equalsIgnoreCase(order) ? ASC : DESC;
  }

  public static Order fromString(String value) {
    return "ASC".equalsIgnoreCase(value) ? ASC : DESC;
  }
}