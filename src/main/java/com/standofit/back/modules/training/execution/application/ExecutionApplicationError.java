package com.standofit.back.modules.training.execution.application;

import com.standofit.back.shared.utils.EnumContract;

public enum ExecutionApplicationError implements EnumContract {
  SESSION_START_FAILED("Failed to start session"),
  SESSION_FINISH_FAILED("Failed to finish session"),
  SESSION_CANCEL_FAILED("Failed to cancel session"),
  SESSION_DELETE_FAILED("Failed to delete session"),
  SESSION_NOTES_UPDATE_FAILED("Failed to update session notes"),
  SESSION_EXERCISE_ADD_FAILED("Failed to add exercise to session"),
  SESSION_EXERCISE_REMOVE_FAILED("Failed to remove exercise from session"),
  SESSION_EXERCISE_REPS_UPDATE_FAILED("Failed to update exercise reps"),
  SESSION_EXERCISE_SETS_UPDATE_FAILED("Failed to update exercise sets"),
  SESSION_EXERCISE_WEIGHT_UPDATE_FAILED("Failed to update exercise weight");

  private final String message;

  ExecutionApplicationError(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
