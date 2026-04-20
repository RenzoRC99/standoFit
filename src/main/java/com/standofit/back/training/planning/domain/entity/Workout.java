package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import com.standofit.back.training.planning.domain.vo.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.standofit.back.shared.domain.utils.CollectionUtils.isNullOrEmpty;

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

        if (id == null) throw new IllegalArgumentException("Workout ID cannot be null");
        if (name == null) throw new IllegalArgumentException("Workout Name cannot be null");

        if (isNullOrEmpty(days)) {
            throw new IllegalArgumentException("A Workout must have at least one day");
        }

        ensureNoDuplicateDayNames(days);

        this.id = id;
        this.name = name;
        this.description = description;
        this.days = List.copyOf(days);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Workout create(WorkoutId id, WorkoutDescription description, WorkoutName name, List<WorkoutDay> days) {
        if (isNullOrEmpty(days)) throw new IllegalArgumentException("Days cannot be null or empty");
        return new Workout(
                id,
                name,
                description,
                days,
                new WorkoutCreatedAt(Instant.now()),
                new WorkoutUpdatedAt(Instant.now())
        );
    }

    public Workout copy(WorkoutId id, WorkoutName name, WorkoutDescription description, List<WorkoutDay> days) {
        return new Workout(
                id,
                name,
                description,
                days,
                this.createdAt,
                new WorkoutUpdatedAt(Instant.now())
        );
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
        if (isNullOrEmpty(newDays)) throw new IllegalArgumentException("Days cannot be null or empty");

        List<WorkoutDay> updatedDays = new ArrayList<>(this.days);
        updatedDays.addAll(newDays);

        return copy(this.id, this.name, this.description, updatedDays);
    }

    public Workout removeDays(List<WorkoutDayId> dayIds) {
        if (isNullOrEmpty(dayIds)) throw new IllegalArgumentException("Day IDs cannot be null or empty");
        validateDayIdsExist(dayIds);

        List<WorkoutDay> updatedDays = this.days.stream()
                .filter(day -> !dayIds.contains(day.getId()))
                .toList();

        return copy(this.id, this.name, this.description, updatedDays);
    }

    public Workout renameDays(Map<WorkoutDayId, WorkoutDayName> dayNames) {
        List<WorkoutDayId> ids = new ArrayList<>(dayNames.keySet());
        if (isNullOrEmpty(ids)) throw new IllegalArgumentException("Day IDs to rename cannot be null or empty");
        validateDayIdsExist(ids);
        
        List<WorkoutDay> updatedDays = this.days.stream()
                .map(day -> dayNames.containsKey(day.getId())
                        ? day.rename(dayNames.get(day.getId()))
                        : day)
                .toList();

        return copy(this.id, this.name, this.description, updatedDays);
    }

    public Workout addExercisesToDay(WorkoutDayId dayId, List<WorkoutExercise> newExercises) {
        if (isNullOrEmpty(newExercises)) throw new IllegalArgumentException("Exercises cannot be null or empty");

        return copy(this.id, this.name, this.description, transformDay(dayId, day -> day.addExercises(newExercises)));
    }

    public Workout updateExercisesInDay(WorkoutDayId dayId, List<WorkoutExercise> updatedExercises) {
        if (isNullOrEmpty(updatedExercises))
            throw new IllegalArgumentException("Updated exercises cannot be null or empty");

        return copy(this.id, this.name, this.description, transformDay(dayId, day -> day.updateExercises(updatedExercises)));
    }

    public Workout removeExercisesFromDay(WorkoutDayId dayId, List<WorkoutExerciseId> exerciseIds) {
        if (isNullOrEmpty(exerciseIds)) throw new IllegalArgumentException("Exercise IDs cannot be null or empty");

        return copy(this.id, this.name, this.description, transformDay(dayId, day -> day.removeExercises(exerciseIds)));
    }

    public Workout reorderDays(List<WorkoutDayId> orderedIds) {
        if (isNullOrEmpty(orderedIds)) throw new IllegalArgumentException("Day IDs to reorder cannot be null or empty");

        validateDayIdsExist(orderedIds);

        if (orderedIds.size() != this.days.size()) {
            throw new IllegalArgumentException("Days count mismatch. Expected: " + this.days.size() + ", got: " + orderedIds.size());
        }

        Map<WorkoutDayId, WorkoutDay> daysById = this.days.stream()
                .collect(Collectors.toMap(WorkoutDay::getId, Function.identity()));

        List<WorkoutDay> reordered = orderedIds.stream()
                .map(daysById::get)
                .toList();

        return copy(this.id, this.name, this.description, reordered);
    }

    private List<WorkoutDay> transformDay(WorkoutDayId id, Function<WorkoutDay, WorkoutDay> transformer) {
        if (this.days.stream().noneMatch(day -> day.getId().equals(id))) {
            throw new IllegalArgumentException("Day not found: " + id.value());
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
                    throw new IllegalArgumentException("Day ID not found: " + id.value());
                });
    }

    private void ensureNoDuplicateDayNames(List<WorkoutDay> days) {
        long uniqueNamesCount = days.stream()
                .map(day -> day.getName().value().toLowerCase().trim())
                .distinct()
                .count();

        if (uniqueNamesCount != days.size()) {
            throw new IllegalArgumentException("Workout days cannot have duplicate names");
        }
    }
}
