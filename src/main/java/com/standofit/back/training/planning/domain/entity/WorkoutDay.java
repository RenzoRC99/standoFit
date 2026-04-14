package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class WorkoutDay {

    private final WorkoutDayId id;
    private final WorkoutDayName name;
    private final List<WorkoutExercise> exercises;

    WorkoutDay(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = (exercises != null) ? List.copyOf(exercises) : List.of();
    }

    static WorkoutDay create(WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDayBuilder(name, exercises).build();
    }

    public WorkoutDayId getId() {
        return id;
    }

    public WorkoutDayName getName() {
        return name;
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }

    WorkoutDay rename(WorkoutDayName name) {
        return new WorkoutDayBuilder(this)
                .withName(name)
                .build();
    }

    WorkoutDay addExercises(List<WorkoutExercise> newExercises) {
        validateIdsNotExist(newExercises.stream()
                .map(WorkoutExercise::getId)
                .toList());

        List<WorkoutExercise> updated = new ArrayList<>(this.exercises);
        updated.addAll(newExercises);

        return new WorkoutDayBuilder(this)
                .withExercises(updated)
                .build();
    }

    WorkoutDay removeExercises(List<WorkoutExerciseId> idsToRemove) {
        validateIdsExist(idsToRemove);

        List<WorkoutExercise> updated = this.exercises.stream()
                .filter(ex -> !idsToRemove.contains(ex.getId()))
                .toList();

        return new WorkoutDayBuilder(this)
                .withExercises(updated)
                .build();
    }

    WorkoutDay updateExercises(List<WorkoutExercise> updatedExercises) {
        validateIdsExist(updatedExercises.stream()
                .map(WorkoutExercise::getId)
                .toList());

        var updatesById = updatedExercises.stream()
                .collect(Collectors.toMap(WorkoutExercise::getId, ex -> ex));

        List<WorkoutExercise> newExercises = this.exercises.stream()
                .map(currentEx -> updatesById.getOrDefault(currentEx.getId(), currentEx))
                .toList();

        return new WorkoutDayBuilder(this)
                .withExercises(newExercises)
                .build();
    }

    private Set<WorkoutExerciseId> getExerciseIds() {
        return this.exercises.stream()
                .map(WorkoutExercise::getId)
                .collect(Collectors.toSet());
    }

    private void validateIdsExist(List<WorkoutExerciseId> ids) {
        ids.stream()
                .filter(id -> !getExerciseIds().contains(id))
                .findFirst()
                .ifPresent(id -> {
                    throw new IllegalArgumentException("Exercise ID not found: " + id.value());
                });
    }

    private void validateIdsNotExist(List<WorkoutExerciseId> ids) {
        ids.stream()
                .filter(getExerciseIds()::contains)
                .findFirst()
                .ifPresent(id -> {
                    throw new IllegalArgumentException("Exercise ID already exists: " + id.value());
                });
    }
}
