package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.training.planning.domain.WorkoutDomainErrors;
import com.standofit.back.training.planning.domain.WorkoutDomainException;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.standofit.back.shared.domain.utils.CollectionUtils.isNullOrEmpty;

public final class WorkoutDay {

    private final WorkoutDayId id;
    private final WorkoutDayName name;
    private final List<WorkoutExercise> exercises;

    WorkoutDay(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {

        ensureNoDuplicateExerciseIds(exercises);

        this.id = id;
        this.name = name;
        this.exercises = (exercises != null) ? List.copyOf(exercises) : List.of();
    }

    public static WorkoutDay create(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDay(
                id,
                name,
                isNullOrEmpty(exercises) ? List.of() : exercises
        );
    }

    public WorkoutDay copy(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        return new WorkoutDay(
                id,
                name,
                exercises
        );
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
        if (isNullOrEmpty(newExercises)) throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISES_CANNOT_BE_NULL_OR_EMPTY.getMessage());

        List<WorkoutExercise> updated = new ArrayList<>(this.exercises);
        updated.addAll(newExercises);

        return copy(this.id, this.name, updated);
    }

    WorkoutDay removeExercises(List<WorkoutExerciseId> idsToRemove) {

        if (isNullOrEmpty(idsToRemove))
            throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISE_ID_NOT_FOUND.getMessage());

        validateIdsExist(idsToRemove);

        List<WorkoutExercise> updated = this.exercises.stream()
                .filter(ex -> !idsToRemove.contains(ex.getId()))
                .toList();

        return copy(this.id, this.name, updated);
    }

    WorkoutDay updateExercises(List<WorkoutExercise> updatedExercises) {
        if (isNullOrEmpty(updatedExercises))
            throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISES_CANNOT_BE_NULL_OR_EMPTY.getMessage());

        List<WorkoutExerciseId> idsToUpdate = updatedExercises.stream().map(WorkoutExercise::getId).toList();
        validateIdsExist(idsToUpdate);

        Map<WorkoutExerciseId, WorkoutExercise> updatesById = updatedExercises.stream()
                .collect(Collectors.toMap(WorkoutExercise::getId, Function.identity()));

        List<WorkoutExercise> newExercises = this.exercises.stream()
                .map(currentEx -> updatesById.getOrDefault(currentEx.getId(), currentEx))
                .toList();

        return copy(this.id, this.name, newExercises);
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
                    throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISE_ID_NOT_FOUND.getMessage());
                });
    }

    private void ensureNoDuplicateExerciseIds(List<WorkoutExercise> exercises) {
        if (exercises == null) return;

        long uniqueIds = exercises.stream()
                .map(WorkoutExercise::getId)
                .distinct()
                .count();

        if (uniqueIds != exercises.size()) {
            throw new WorkoutDomainException(WorkoutDomainErrors.EXERCISE_ID_ALREADY_EXISTS.getMessage());
        }
    }
}
