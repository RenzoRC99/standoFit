package com.standofit.back.modules.training.planning.domain;

import com.standofit.back.shared.utils.EnumContract;

public enum WorkoutDomainErrors implements EnumContract {
  DAY_NAME_ALREADY_EXISTS("Day name already exists"),
  DAY_ID_NOT_FOUND("Day ID not found"),
  EXERCISE_ID_NOT_FOUND("Exercise ID not found"),
  EXERCISE_ID_ALREADY_EXISTS("Exercise ID already exists"),
  DAYS_CANNOT_BE_NULL_OR_EMPTY("Days cannot be null or empty"),
  DAY_IDS_CANNOT_BE_NULL_OR_EMPTY("Day IDs cannot be null or empty"),
  DAYS_COUNT_MISMATCH("Days count mismatch"),
  EXERCISES_CANNOT_BE_NULL_OR_EMPTY("Exercises cannot be null or empty");

  private final String message;

  WorkoutDomainErrors(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
