package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import com.standofit.back.training.planning.domain.WorkoutErrors;
import com.standofit.back.training.planning.domain.WorkoutException;
import com.standofit.back.training.planning.domain.vo.*;

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

    Workout(WorkoutId id, WorkoutName name, WorkoutDescription description,
            List<WorkoutDay> days,
            WorkoutCreatedAt createdAt, WorkoutUpdatedAt updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.days = days;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Workout create(
            WorkoutDescription description,
            WorkoutName name,
            List<WorkoutDay> days
    ) {
        validateDaysNotEmpty(days);
        return new WorkoutBuilder
                (description, name, days)
                .build();
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
        return new WorkoutBuilder(this)
                .withName(name)
                .build();
    }

    public Workout changeDescription(WorkoutDescription description) {
        return new WorkoutBuilder(this)
                .withDescription(description)
                .build();
    }

    public Workout addDays(List<WorkoutDay> newDays) {
        validateDaysNotEmpty(newDays);

        Set<String> existingNames = this.days.stream()
                .map(day -> day.getName().value().toLowerCase())
                .collect(Collectors.toSet());

        List<String> newNames = newDays.stream()
                .map(day -> day.getName().value().toLowerCase())
                .toList();

        validateNoDuplicateNames(newNames);

        newNames.stream()
                .filter(existingNames::contains)
                .findFirst()
                .ifPresent(name -> {
                    throw new WorkoutException(WorkoutErrors.DAY_NAME_ALREADY_EXISTS, name);
                });

        List<WorkoutDay> updatedDays = new ArrayList<>(this.days);
        updatedDays.addAll(newDays);

        return new WorkoutBuilder(this)
                .withDays(updatedDays)
                .build();
    }

    public Workout removeDays(List<WorkoutDayId> dayIds) {
        validateDayIdsNotEmpty(dayIds);

        validateDayIdsExist(dayIds);

        List<WorkoutDay> updatedDays = this.days.stream()
                .filter(day -> !dayIds.contains(day.getId()))
                .toList();

        return new WorkoutBuilder(this)
                .withDays(updatedDays)
                .build();
    }

    public Workout renameDays(Map<WorkoutDayId, WorkoutDayName> dayNames) {
        List<WorkoutDayId> ids = new ArrayList<>(dayNames.keySet());
        validateDayIdsToRenameNotEmpty(ids);

        validateDayIdsExist(ids);

        List<String> names = dayNames.values().stream()
                .map(vo -> vo.value().toLowerCase())
                .toList();

        validateNoDuplicateNames(names);

        List<WorkoutDay> updatedDays = this.days.stream()
                .map(day -> dayNames.containsKey(day.getId())
                        ? day.rename(dayNames.get(day.getId()))
                        : day)
                .toList();

        return new WorkoutBuilder(this)
                .withDays(updatedDays)
                .build();
    }

    public Workout addExercisesToDay(WorkoutDayId dayId, List<WorkoutExercise> newExercises) {
        validateExercisesNotEmpty(newExercises);

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.addExercises(newExercises)))
                .build();
    }

    public Workout updateExercisesInDay(WorkoutDayId dayId, List<WorkoutExercise> updatedExercises) {
        validateUpdatedExercisesNotEmpty(updatedExercises);

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.updateExercises(updatedExercises)))
                .build();
    }

    public Workout removeExercisesFromDay(WorkoutDayId dayId, List<WorkoutExerciseId> exerciseIds) {
        validateExerciseIdsNotEmpty(exerciseIds);

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.removeExercises(exerciseIds)))
                .build();
    }

    public Workout reorderDays(List<WorkoutDayId> orderedIds) {
        validateDayIdsToReorderNotEmpty(orderedIds);

        List<String> ids = orderedIds.stream()
                .map(id -> id.value().toString())
                .toList();

        validateNoDuplicateNames(ids);

        validateDayIdsExist(orderedIds);

        if (orderedIds.size() != this.days.size()) {
            throw new WorkoutException(WorkoutErrors.DAYS_COUNT_MISMATCH);
        }

        List<WorkoutDay> reordered = orderedIds.stream()
                .map(id -> this.days.stream()
                        .filter(day -> day.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new WorkoutException(WorkoutErrors.DAY_ID_NOT_FOUND, id.value().toString())))
                .toList();

        return new WorkoutBuilder(this)
                .withDays(reordered)
                .build();
    }

    private List<WorkoutDay> transformDay(WorkoutDayId id, Function<WorkoutDay, WorkoutDay> transformer) {
        if (this.days.stream().noneMatch(day -> day.getId().equals(id))) {
            throw new WorkoutException(WorkoutErrors.DAY_ID_NOT_FOUND, id.value().toString());
        }

        return this.days.stream()
                .map(day -> day.getId().equals(id) ? transformer.apply(day) : day)
                .toList();
    }

    private Set<WorkoutDayId> getDayIds() {
        return this.days.stream()
                .map(WorkoutDay::getId)
                .collect(Collectors.toSet());
    }

    private void validateDayIdsExist(List<WorkoutDayId> ids) {
        ids.stream()
                .filter(id -> !getDayIds().contains(id))
                .findFirst()
                .ifPresent(id -> {
                    throw new WorkoutException(WorkoutErrors.DAY_ID_NOT_FOUND, id.value().toString());
                });
    }

    private void validateNoDuplicateNames(List<String> names) {
        names.stream()
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()))
                .values()
                .stream()
                .filter(count -> count > 1)
                .findFirst()
                .ifPresent(count -> {
                    throw new WorkoutException(WorkoutErrors.DUPLICATE_DAY_NAME);
                });
    }

    private static void validateDaysNotEmpty(List<WorkoutDay> days) {
        if (days == null || days.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.DAYS_NOT_EMPTY);
        }
    }

    private static void validateDayIdsNotEmpty(List<WorkoutDayId> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.DAY_IDS_NOT_EMPTY);
        }
    }

    private static void validateDayIdsToRenameNotEmpty(List<WorkoutDayId> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.DAY_IDS_TO_RENAME_NOT_EMPTY);
        }
    }

    private static void validateDayIdsToReorderNotEmpty(List<WorkoutDayId> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.DAY_IDS_TO_REORDER_NOT_EMPTY);
        }
    }

    private static void validateExercisesNotEmpty(List<WorkoutExercise> exercises) {
        if (exercises == null || exercises.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.EXERCISES_NOT_EMPTY);
        }
    }

    private static void validateUpdatedExercisesNotEmpty(List<WorkoutExercise> exercises) {
        if (exercises == null || exercises.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.UPDATED_EXERCISES_NOT_EMPTY);
        }
    }

    private static void validateExerciseIdsNotEmpty(List<WorkoutExerciseId> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new WorkoutException(WorkoutErrors.EXERCISE_IDS_NOT_EMPTY);
        }
    }
}
