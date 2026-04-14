package com.standofit.back.training.planning.domain;

public enum WorkoutErrors {

    DAYS_NOT_EMPTY("Must provide at least one day"),
    DAY_IDS_NOT_EMPTY("Must provide at least one day ID to remove"),
    DAY_IDS_TO_RENAME_NOT_EMPTY("Must provide at least one day ID to rename"),
    DAY_IDS_TO_REORDER_NOT_EMPTY("Must provide at least one day ID to reorder"),
    EXERCISES_NOT_EMPTY("Must provide at least one exercise to add"),
    UPDATED_EXERCISES_NOT_EMPTY("Must provide at least one exercise to update"),
    EXERCISE_IDS_NOT_EMPTY("Must provide at least one exercise ID to remove"),
    DAY_NAME_ALREADY_EXISTS("Day with name '%s' already exists"),
    DAY_ID_NOT_FOUND("Day with id '%s' not found"),
    DUPLICATE_DAY_NAME("Duplicate day name in the list"),
    DUPLICATE_DAY_ID("Duplicate day ID in the list"),
    DAYS_COUNT_MISMATCH("The number of IDs must match the current number of days");

    private final String message;

    WorkoutErrors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
