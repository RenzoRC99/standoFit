package com.standofit.back.shared.infrastructure.bus.command;

import com.standofit.back.shared.utils.EnumContract;

public enum CommandBusErrors implements EnumContract {
  COMMAND_HANDLER_NOT_FOUND("No command handler found for: %s"),
  DUPLICATE_COMMAND_HANDLER("Duplicate command handler for: %s");

  private final String message;

  CommandBusErrors(String message) {
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
