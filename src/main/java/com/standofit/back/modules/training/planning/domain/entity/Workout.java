package com.standofit.back.modules.training.planning.domain.entity;

import static com.standofit.back.shared.utils.CollectionUtils.isNullOrEmpty;
import static com.standofit.back.shared.utils.CollectionUtils.isNullOrEmptyForMap;

import com.standofit.back.modules.training.planning.domain.WorkoutDomainErrors;
import com.standofit.back.modules.training.planning.domain.WorkoutDomainException;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Workout extends AggregateRoot {

  private final WorkoutId id;
  private final WorkoutName name;
  private final WorkoutDescription description;
  private final List<WorkoutDay> days;
  private final WorkoutCreatedAt createdAt;
  private final WorkoutUpdatedAt updatedAt;

  Workout(
      WorkoutId id,
      WorkoutName name,
      WorkoutDescription description,
      List<WorkoutDay> days,
      WorkoutCreatedAt createdAt,
      WorkoutUpdatedAt updatedAt) {
    if (isNullOrEmpty(days)) {
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAYS_CANNOT_BE_NULL_OR_EMPTY.getMessage());
    }
    ensureNoDuplicateDayNames(days);
    this.id = id;
    this.name = name;
    this.description = description;
    this.days = List.copyOf(days);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Workout create(
      WorkoutId id, WorkoutDescription description, WorkoutName name, List<WorkoutDay> days) {
    if (isNullOrEmpty(days))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAYS_CANNOT_BE_NULL_OR_EMPTY.getMessage());
    return new Workout(
        id,
        name,
        description,
        days,
        new WorkoutCreatedAt(Instant.now()),
        new WorkoutUpdatedAt(Instant.now()));
  }

  Workout copy(
      WorkoutId id, WorkoutName name, WorkoutDescription description, List<WorkoutDay> days) {
    return new Workout(
        id, name, description, days, this.createdAt, new WorkoutUpdatedAt(Instant.now()));
  }

  public WorkoutId getId() {
    return id;
  }

  public WorkoutName getName() {
    return name;
  }

  public WorkoutDescription getDescription() {
    return description;
  }

  public List<WorkoutDay> getDays() {
    return days;
  }

  public WorkoutCreatedAt getCreatedAt() {
    return createdAt;
  }

  public WorkoutUpdatedAt getUpdatedAt() {
    return updatedAt;
  }

  public Workout renameWorkout(WorkoutName name) {
    return copy(this.id, name, this.description, this.days);
  }

  public Workout changeDescription(WorkoutDescription description) {
    return copy(this.id, this.name, description, this.days);
  }

  public Workout addDays(List<WorkoutDay> newDays) {
    if (isNullOrEmpty(newDays))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAYS_CANNOT_BE_NULL_OR_EMPTY.getMessage());

    List<WorkoutDay> updatedDays = new ArrayList<>(this.days);
    updatedDays.addAll(newDays);

    return copy(this.id, this.name, this.description, updatedDays);
  }

  public Workout removeDays(List<WorkoutDayId> dayIds) {
    if (isNullOrEmpty(dayIds))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAY_IDS_CANNOT_BE_NULL_OR_EMPTY.getMessage());
    validateDayIdsExist(dayIds);

    List<WorkoutDay> updatedDays =
        this.days.stream().filter(day -> !dayIds.contains(day.getId())).toList();

    return copy(this.id, this.name, this.description, updatedDays);
  }

  public Workout renameDays(Map<WorkoutDayId, WorkoutDayName> dayNames) {
    if (isNullOrEmptyForMap(dayNames))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAY_IDS_CANNOT_BE_NULL_OR_EMPTY.getMessage());
    List<WorkoutDayId> ids = new ArrayList<>(dayNames.keySet());
    validateDayIdsExist(ids);

    List<WorkoutDay> updatedDays =
        this.days.stream()
            .map(
                day ->
                    dayNames.containsKey(day.getId()) ? day.rename(dayNames.get(day.getId())) : day)
            .toList();

    return copy(this.id, this.name, this.description, updatedDays);
  }

  public Workout addExercisesToDay(WorkoutDayId dayId, List<WorkoutExercise> newExercises) {
    if (isNullOrEmpty(newExercises))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.EXERCISES_CANNOT_BE_NULL_OR_EMPTY.getMessage());

    return copy(
        this.id,
        this.name,
        this.description,
        transformDay(dayId, day -> day.addExercises(newExercises)));
  }

  public Workout updateExercisesInDay(WorkoutDayId dayId, List<WorkoutExercise> updatedExercises) {
    if (isNullOrEmpty(updatedExercises))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.EXERCISES_CANNOT_BE_NULL_OR_EMPTY.getMessage());

    return copy(
        this.id,
        this.name,
        this.description,
        transformDay(dayId, day -> day.updateExercises(updatedExercises)));
  }

  public Workout removeExercisesFromDay(WorkoutDayId dayId, List<WorkoutExerciseId> exerciseIds) {
    if (isNullOrEmpty(exerciseIds))
      throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISE_ID_NOT_FOUND.getMessage());

    return copy(
        this.id,
        this.name,
        this.description,
        transformDay(dayId, day -> day.removeExercises(exerciseIds)));
  }

  public Workout reorderDays(List<WorkoutDayId> orderedIds) {
    if (isNullOrEmpty(orderedIds))
      throw new WorkoutDomainException(
          WorkoutDomainErrors.DAY_IDS_CANNOT_BE_NULL_OR_EMPTY.getMessage());

    validateDayIdsExist(orderedIds);

    if (orderedIds.size() != this.days.size()) {
      throw new WorkoutDomainException(WorkoutDomainErrors.DAYS_COUNT_MISMATCH.getMessage());
    }

    Map<WorkoutDayId, WorkoutDay> daysById =
        this.days.stream().collect(Collectors.toMap(WorkoutDay::getId, Function.identity()));

    List<WorkoutDay> reordered = orderedIds.stream().map(daysById::get).toList();

    return copy(this.id, this.name, this.description, reordered);
  }

  private List<WorkoutDay> transformDay(
      WorkoutDayId id, Function<WorkoutDay, WorkoutDay> transformer) {
    if (this.days.stream().noneMatch(day -> day.getId().equals(id))) {
      throw new WorkoutDomainException(WorkoutDomainErrors.DAY_ID_NOT_FOUND.getMessage());
    }

    return this.days.stream()
        .map(day -> day.getId().equals(id) ? transformer.apply(day) : day)
        .toList();
  }

  private Set<WorkoutDayId> getDayIds() {
    return this.days.stream().map(WorkoutDay::getId).collect(Collectors.toSet());
  }

  private void validateDayIdsExist(List<WorkoutDayId> ids) {
    ids.stream()
        .filter(id -> !getDayIds().contains(id))
        .findFirst()
        .ifPresent(
            id -> {
              throw new WorkoutDomainException(WorkoutDomainErrors.DAY_ID_NOT_FOUND.getMessage());
            });
  }

  private void ensureNoDuplicateDayNames(List<WorkoutDay> days) {
    long uniqueNamesCount =
        days.stream().map(day -> day.getName().value().toLowerCase().trim()).distinct().count();

    if (uniqueNamesCount != days.size()) {
      throw new WorkoutDomainException(WorkoutDomainErrors.DAY_NAME_ALREADY_EXISTS.getMessage());
    }
  }
}
