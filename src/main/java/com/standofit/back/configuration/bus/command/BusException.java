package com.standofit.back.configuration.bus.command;

public class BusException extends RuntimeException {
  public BusException(String message) {
    super(message);
  }
}
