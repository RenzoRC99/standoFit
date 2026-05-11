package com.standofit.back.configuration.bus.query;

enum QueryBusErrors {
  DUPLICATE_QUERY_HANDLER("Duplicate query handler for: %s"),
  QUERY_HANDLER_NOT_FOUND("Query handler not found for: %s");

  private final String messageTemplate;

  QueryBusErrors(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  String getMessage(String param) {
    return String.format(messageTemplate, param);
  }
}
