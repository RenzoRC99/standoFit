package com.standofit.back.configuration.bus.command;

public enum BusErrors {
  COMMAND_HANDLER_NOT_FOUND("No command handler found for: %s"),
  QUERY_HANDLER_NOT_FOUND("No query handler found for: %s"),
  DUPLICATE_COMMAND_HANDLER("Duplicate command handler for: %s"),
  DUPLICATE_QUERY_HANDLER("Duplicate query handler for: %s"),
  APPLICATION_EVENT_HANDLER_NOT_FOUND("No application event handler found for: %s");

  private final String messageTemplate;

  BusErrors(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  public String getMessage(String typeName) {
    return String.format(messageTemplate, typeName);
  }
}
