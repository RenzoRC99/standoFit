package com.standofit.back.modules.training.execution.application.event;

public enum ExecutionActivityType {
  SESSION_STARTED("Started session"),
  SESSION_FINISHED("Finished session"),
  SESSION_CANCELLED("Cancelled session"),
  SESSION_DELETED("Deleted session"),
  SESSION_NOTES_UPDATED("Updated session notes"),
  SESSION_EXERCISE_ADDED("Added exercise to session"),
  SESSION_EXERCISE_REMOVED("Removed exercise from session"),
  SESSION_EXERCISE_SETS_UPDATED("Updated exercise sets"),
  SESSION_EXERCISE_REPS_UPDATED("Updated exercise reps"),
  SESSION_EXERCISE_WEIGHT_UPDATED("Updated exercise weight"),
  SESSION_QUERIED("Queried session"),
  SESSIONS_SEARCHED("Searched sessions");

  private final String defaultDescription;

  ExecutionActivityType(String defaultDescription) {
    this.defaultDescription = defaultDescription;
  }

  public String getDefaultDescription() {
    return defaultDescription;
  }
}
