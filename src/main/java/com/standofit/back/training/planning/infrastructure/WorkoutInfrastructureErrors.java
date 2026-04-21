package com.standofit.back.training.planning.infrastructure;

import com.standofit.back.shared.utils.EnumContract;

public enum WorkoutInfrastructureErrors implements EnumContract {
  SAVE_FAILED("Failed to save workout"),
  FIND_FAILED("Failed to find workout"),
  DELETE_FAILED("Failed to delete workout"),
  DB_CONNECTION_ERROR("Database connection error", "08"),
  DB_TIMEOUT("Database timeout"),
  DB_CONSTRAINT_VIOLATION("Database constraint violation", "23"),
  DB_UNKNOWN_ERROR("Unknown database error");

  private final String message;
  private final String sqlState;

  WorkoutInfrastructureErrors(String message) {
    this(message, null);
  }

  WorkoutInfrastructureErrors(String message, String sqlState) {
    this.message = message;
    this.sqlState = sqlState;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getSqlState() {
    return sqlState;
  }

  public boolean matchesSqlState(String sqlState) {
    return sqlState != null && this.sqlState != null && sqlState.startsWith(this.sqlState);
  }
}
