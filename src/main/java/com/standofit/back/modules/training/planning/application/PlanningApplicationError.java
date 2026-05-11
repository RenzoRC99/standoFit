package com.standofit.back.modules.training.planning.application;

import com.standofit.back.shared.utils.EnumContract;

public enum PlanningApplicationError implements EnumContract {
  WORKOUT_PLAN_FAILED("Failed to plan workout"),
  WORKOUT_DELETE_FAILED("Failed to delete workout"),
  WORKOUT_ARCHIVE_FAILED("Failed to archive workout"),
  WORKOUT_RENAME_FAILED("Failed to rename workout"),
  WORKOUT_DUPLICATE_FAILED("Failed to duplicate workout"),
  WORKOUT_DESCRIPTION_CHANGE_FAILED("Failed to change workout description"),
  WORKOUT_QUERY_FAILED("Failed to query workout"),
  WORKOUT_SEARCH_FAILED("Failed to search workouts"),
  WORKOUT_DAY_ADD_FAILED("Failed to add day to workout"),
  WORKOUT_DAY_REMOVE_FAILED("Failed to remove day from workout"),
  WORKOUT_DAYS_REORDER_FAILED("Failed to reorder workout days"),
  WORKOUT_NOT_FOUND("Workout not found");

  private final String message;

  PlanningApplicationError(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
