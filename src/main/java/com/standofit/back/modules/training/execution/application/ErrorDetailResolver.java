package com.standofit.back.modules.training.execution.application;

public final class ErrorDetailResolver {

  private ErrorDetailResolver() {}

  public static String resolve(Exception e) {
    String message = e.getMessage();
    if (e.getCause() != null) {
      return message + " | cause: " + e.getCause().getMessage();
    }
    return message;
  }
}
