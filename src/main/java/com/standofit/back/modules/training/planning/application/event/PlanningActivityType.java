package com.standofit.back.modules.training.planning.application.event;

public enum PlanningActivityType {
  WORKOUT_PLANNED("Planned workout"),
  WORKOUT_DELETED("Deleted workout"),
  WORKOUT_RENAMED("Renamed workout"),
  WORKOUT_DUPLICATED("Duplicated workout"),
  WORKOUT_DESCRIPTION_CHANGED("Changed description"),
  WORKOUT_DAY_ADDED("Added day to workout"),
  WORKOUT_DAY_REMOVED("Removed day from workout"),
  WORKOUT_DAYS_REORDERED("Reordered days"),
  WORKOUT_DAY_RENAMED("Renamed day"),
  WORKOUT_EXERCISES_REPLACED("Replaced exercises in day"),
  WORKOUT_QUERIED("Queried workout"),
  WORKOUTS_SEARCHED("Searched workouts");

  private final String defaultDescription;

  PlanningActivityType(String defaultDescription) {
    this.defaultDescription = defaultDescription;
  }

  public String getDefaultDescription() {
    return defaultDescription;
  }
}
