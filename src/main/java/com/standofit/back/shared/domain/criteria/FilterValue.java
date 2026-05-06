package com.standofit.back.shared.domain.criteria;

/**
 * Value Object for filter value.
 *
 * @author standofit
 * @version 1.0
 */
public record FilterValue(String value) {
  public FilterValue {
    if (value == null) {
      throw new IllegalArgumentException("Filter value cannot be null");
    }
  }
}