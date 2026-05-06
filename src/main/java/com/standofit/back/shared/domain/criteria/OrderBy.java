package com.standofit.back.shared.domain.criteria;

/**
 * Value Object representing the field name to order by.
 *
 * @author standofit
 * @version 1.0
 */
public record OrderBy(String value) {
  public OrderBy {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("OrderBy field cannot be empty");
    }
  }
}