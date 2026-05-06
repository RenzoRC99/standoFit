package com.standofit.back.shared.domain.criteria;

/**
 * Value Object for filter field name.
 *
 * @author standofit
 * @version 1.0
 */
public record FilterField(String value) {
  public FilterField {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Filter field cannot be empty");
    }
  }
}