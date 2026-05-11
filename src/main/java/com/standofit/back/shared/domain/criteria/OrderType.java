package com.standofit.back.shared.domain.criteria;

/**
 * Order type (ASC, DESC, NONE) — like PHP OrderType.
 *
 * @author standofit
 * @version 1.0
 */
public enum OrderType {
  ASC("asc"),
  DESC("desc"),
  NONE("none");

  private final String value;

  OrderType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public boolean isNone() {
    return this == NONE;
  }

  public static OrderType fromString(String value) {
    if (value == null || value.isBlank()) {
      return NONE;
    }
    return switch (value.toLowerCase()) {
      case "asc" -> ASC;
      case "desc" -> DESC;
      default -> NONE;
    };
  }
}
