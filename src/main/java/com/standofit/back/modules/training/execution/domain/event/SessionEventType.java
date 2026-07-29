package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.shared.utils.EnumContract;

public enum SessionEventType implements EnumContract {
  SESSION_STARTED("session.started"),
  SESSION_FINISHED("session.finished"),
  SESSION_CANCELLED("session.cancelled"),
  SESSION_NOTES_CHANGED("session.notes_changed"),
  EXERCISE_LOG_ADDED("session.exercise_log_added"),
  EXERCISE_LOG_REMOVED("session.exercise_log_removed"),
  EXERCISE_LOG_SETS_UPDATED("session.exercise_log_sets_updated"),
  EXERCISE_LOG_REPS_UPDATED("session.exercise_log_reps_updated"),
  EXERCISE_LOG_WEIGHT_UPDATED("session.exercise_log_weight_updated");

  private final String message;

  SessionEventType(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
