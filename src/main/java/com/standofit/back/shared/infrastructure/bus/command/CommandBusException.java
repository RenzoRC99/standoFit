package com.standofit.back.shared.infrastructure.bus.command;

public class CommandBusException extends RuntimeException {
  public CommandBusException(String message) {
    super(message);
  }
}
