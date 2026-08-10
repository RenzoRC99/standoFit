package com.standofit.back.modules.training.execution.domain;

import com.standofit.back.shared.utils.EnumContract;

public enum SessionDomainErrors implements EnumContract {
  SESSION_NOT_FOUND("Session not found"),
  SESSION_CANNOT_BE_MODIFIED("Session cannot be modified"),
  SESSION_ALREADY_FINISHED("Session already finished"),
  SESSION_ALREADY_CANCELLED("Session already cancelled"),
  SESSION_CANNOT_BE_FINISHED("Session cannot be finished"),
  SESSION_CANNOT_BE_CANCELLED("Session cannot be cancelled"),
  LOG_NOT_FOUND("Log not found"),
  EXERCISE_NOT_FOUND("Exercise not found in catalog"),
  DAY_NOT_FOUND("Workout day not found in planning"),
  REPLAY_EMPTY_EVENTS("Cannot replay empty event sequence"),
  REPLAY_REQUIRES_SESSION_STARTED("First event must be SessionStarted"),
  REPLAY_UNHANDLED_EVENT("Replay cannot handle event of type '%s'");

  private final String message;

  SessionDomainErrors(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
