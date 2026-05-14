package com.standofit.back.modules.training.planning.infrastructure;

import com.standofit.back.shared.utils.EnumContract;

public enum WorkoutInfrastructureErrors implements EnumContract {
  SAVE_FAILED("Failed to save workout"),
  FIND_FAILED("Failed to find workout"),
  WORKOUT_NOT_FOUND("Workout not found: %s"),
  DELETE_FAILED("Failed to delete workout");

  private final String message;

  WorkoutInfrastructureErrors(String message) {
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
