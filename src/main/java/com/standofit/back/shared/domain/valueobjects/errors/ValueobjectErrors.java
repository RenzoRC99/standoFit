package com.standofit.back.shared.domain.valueobjects.errors;

import com.standofit.back.shared.utils.EnumContract;

public enum ValueobjectErrors implements EnumContract {
  NULL_VALUE("Cannot be null"),
  INVALID_LENGTH_RANGE("Must be between %d and %d characters"),
  NOT_EMPTY("Cannot be empty"),
  INT_BIGGER_THAN("Cannot be bigger than %d"),
  INT_SMALLER_THAN("Cannot be smaller than %d");

  private final String message;

  ValueobjectErrors(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
