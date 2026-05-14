package com.standofit.back.modules.training.execution.infrastructure;

import com.standofit.back.shared.utils.EnumContract;

public enum SessionInfrastructureErrors implements EnumContract {
  SAVE_FAILED("Failed to save session"),
  FIND_FAILED("Failed to find session"),
  DELETE_FAILED("Failed to delete session");

  private final String message;

  SessionInfrastructureErrors(String message) {
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
