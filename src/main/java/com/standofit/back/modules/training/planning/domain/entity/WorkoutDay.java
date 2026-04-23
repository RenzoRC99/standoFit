package com.standofit.back.modules.training.planning.domain.entity;

import com.standofit.back.modules.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class WorkoutDay {

    private final WorkoutDayId id;
    private final WorkoutDayName name;
    private final List<WorkoutExercise> exercises;

    WorkoutDay(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises != null ? List.copyOf(exercises) : List.of();
    }

    public static WorkoutDay create(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDay(id, name, exercises);
    }

    public WorkoutDay copy(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDay(id, name, exercises);
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
        return copy(this.id, name, this.exercises);
    }

    WorkoutDay addExercises(List<WorkoutExercise> newExercises) {
        List<WorkoutExercise> updated = new ArrayList<>(this.exercises);
        updated.addAll(newExercises);
        return copy(this.id, this.name, updated);
    }

    WorkoutDay removeExercises(List<WorkoutExerciseId> idsToRemove) {
        List<WorkoutExercise> updated =
                this.exercises.stream().filter(ex -> !idsToRemove.contains(ex.getId())).toList();
        return copy(this.id, this.name, updated);
    }

    WorkoutDay updateExercises(List<WorkoutExercise> updatedExercises) {
        Map<WorkoutExerciseId, WorkoutExercise> updatesById =
                updatedExercises.stream()
                        .collect(Collectors.toMap(WorkoutExercise::getId, Function.identity()));

        List<WorkoutExercise> newExercises =
                this.exercises.stream()
                        .map(currentEx -> updatesById.getOrDefault(currentEx.getId(), currentEx))
                        .toList();

        return copy(this.id, this.name, newExercises);
    }
}
