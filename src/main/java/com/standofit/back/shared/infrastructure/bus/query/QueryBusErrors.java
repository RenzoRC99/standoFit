package com.standofit.back.shared.infrastructure.bus.query;

import com.standofit.back.shared.utils.EnumContract;

public enum QueryBusErrors implements EnumContract {
  DUPLICATE_QUERY_HANDLER("Duplicate query handler for: %s"),
  QUERY_HANDLER_NOT_FOUND("Query handler not found for: %s");

  private final String message;

  QueryBusErrors(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getMessage(Object... args) {
    return args.length > 0 ? String.format(message, args) : message;
  }
}
