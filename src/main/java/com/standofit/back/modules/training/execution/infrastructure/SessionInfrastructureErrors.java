package com.standofit.back.modules.training.execution.infrastructure;

import com.standofit.back.shared.utils.EnumContract;

public enum SessionInfrastructureErrors implements EnumContract {

    // == Repository errors ==
    SAVE_FAILED("Failed to save session"),
    FIND_FAILED("Failed to find session"),
    DELETE_FAILED("Failed to delete session"),
    DB_CONNECTION_ERROR("Database connection error", "08"),
    DB_TIMEOUT("Database timeout"),
    DB_CONSTRAINT_VIOLATION("Database constraint violation", "23"),
    DB_UNKNOWN_ERROR("Unknown database error");

    private final String message;
    private final String sqlState;

    SessionInfrastructureErrors(String message) {
        this(message, null);
    }

    SessionInfrastructureErrors(String message, String sqlState) {
        this.message = message;
        this.sqlState = sqlState;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return args.length > 0 ? String.format(message, args) : message;
    }

    public String getSqlState() {
        return sqlState;
    }

    public boolean matchesSqlState(String sqlState) {
        return sqlState != null && this.sqlState != null && sqlState.startsWith(this.sqlState);
    }
}