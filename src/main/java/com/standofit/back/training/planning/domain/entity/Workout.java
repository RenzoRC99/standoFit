package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
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
        validateNotEmpty(days, "days");
        return new WorkoutBuilder
                (description, name, days)
                .build();
    }

    private static void validateNotEmpty(List<?> list, String context) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Must provide at least one " + context);
        }
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
        validateNotEmpty(newDays, "days to add");

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
                    throw new IllegalArgumentException("Day with name '" + name + "' already exists");
                });

        List<WorkoutDay> updatedDays = new ArrayList<>(this.days);
        updatedDays.addAll(newDays);

        return new WorkoutBuilder(this)
                .withDays(updatedDays)
                .build();
    }

    public Workout removeDays(List<WorkoutDayId> dayIds) {
        validateNotEmpty(dayIds, "day IDs to remove");

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
        validateNotEmpty(ids, "day IDs to rename");

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
        validateNotEmpty(newExercises, "exercises to add");

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.addExercises(newExercises)))
                .build();
    }

    public Workout updateExercisesInDay(WorkoutDayId dayId, List<WorkoutExercise> updatedExercises) {
        validateNotEmpty(updatedExercises, "updated exercises");

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.updateExercises(updatedExercises)))
                .build();
    }

    public Workout removeExercisesFromDay(WorkoutDayId dayId, List<WorkoutExerciseId> exerciseIds) {
        validateNotEmpty(exerciseIds, "exercise IDs to remove");

        return new WorkoutBuilder(this)
                .withDays(transformDay(dayId, day -> day.removeExercises(exerciseIds)))
                .build();
    }

    public Workout reorderDays(List<WorkoutDayId> orderedIds) {
        validateNotEmpty(orderedIds, "day IDs to reorder");

        List<String> ids = orderedIds.stream()
                .map(id -> id.value().toString())
                .toList();

        validateNoDuplicateNames(ids);

        validateDayIdsExist(orderedIds);

        if (orderedIds.size() != this.days.size()) {
            throw new IllegalArgumentException("The number of IDs must match the current number of days");
        }

        List<WorkoutDay> reordered = orderedIds.stream()
                .map(id -> this.days.stream()
                        .filter(day -> day.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Day ID not found: " + id.value())))
                .toList();

        return new WorkoutBuilder(this)
                .withDays(reordered)
                .build();
    }

    private List<WorkoutDay> transformDay(WorkoutDayId id, Function<WorkoutDay, WorkoutDay> transformer) {
        if (this.days.stream().noneMatch(day -> day.getId().equals(id))) {
            throw new IllegalArgumentException("Workout day not found: " + id.value());
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
                    throw new IllegalArgumentException("Day with id '" + id.value() + "' not found");
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
                    throw new IllegalArgumentException("Duplicate day name in the list");
                });
    }
}
