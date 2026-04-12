package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

public final class WorkoutDay {
    private final WorkoutDayId id;
    private final WorkoutDayName name;
    private final List<WorkoutExercise> exercises;

    WorkoutDay(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = (exercises != null) ? List.copyOf(exercises) : List.of();
    }

    public WorkoutDayId getId() { return id; }
    public WorkoutDayName getName() { return name; }
    public List<WorkoutExercise> getExercises() { return exercises; }

    static WorkoutDay create(WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDayBuilder(name, exercises).build();
    }

    WorkoutDay rename(WorkoutDayName name) {
        return new WorkoutDayBuilder(this)
                .withName(name)
                .build();
    }

    WorkoutDay addExercises(List<WorkoutExercise> newExercises) {
        List<WorkoutExercise> updated = new ArrayList<>(this.exercises);
        updated.addAll(newExercises);

        return new WorkoutDayBuilder(this)
                .withExercises(updated)
                .build();
    }

    WorkoutDay removeExercises(List<WorkoutExerciseId> idsToRemove) {
        List<WorkoutExercise> updated = this.exercises.stream()
                .filter(ex -> !idsToRemove.contains(ex.getId()))
                .toList();

        return new WorkoutDayBuilder(this)
                .withExercises(updated)
                .build();
    }

    WorkoutDay updateExercises(List<WorkoutExercise> updatedExercises) {

        var updatesById = updatedExercises.stream()
                .collect(Collectors.toMap(WorkoutExercise::getId, ex -> ex));

        List<WorkoutExercise> newExercises = this.exercises.stream()
                .map(currentEx -> updatesById.getOrDefault(currentEx.getId(), currentEx))
                .toList();

        return new WorkoutDayBuilder(this)
                .withExercises(newExercises)
                .build();
    }

    WorkoutDay reorderExercises(List<WorkoutExerciseId> orderedIds) {
        if (orderedIds.size() != this.exercises.size()) {
            throw new IllegalArgumentException("The number of IDs must match the current number of exercises");
        }

        List<WorkoutExercise> reordered = orderedIds.stream()
                .map(id -> this.exercises.stream()
                        .filter(ex -> ex.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Exercise ID not found: " + id.value())))
                .toList();

        return new WorkoutDayBuilder(this)
                .withExercises(reordered)
                .build();
    }
}
