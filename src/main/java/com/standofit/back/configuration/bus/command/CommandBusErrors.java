package com.standofit.back.configuration.bus.command;

public enum CommandBusErrors {
  COMMAND_HANDLER_NOT_FOUND("No command handler found for: %s"),
  DUPLICATE_COMMAND_HANDLER("Duplicate command handler for: %s");

  private final String messageTemplate;

  CommandBusErrors(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  public String getMessage(String typeName) {
    return String.format(messageTemplate, typeName);
  }
}
