package com.standofit.back.configuration.bus.command;

public class CommandBusException extends RuntimeException {
  public CommandBusException(String message) {
    super(message);
  }
}
