package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.List;
import java.util.UUID;

final class WorkoutDayBuilder {
    private final WorkoutDayId id;
    private WorkoutDayName name;
    private List<WorkoutExercise> exercises;

    WorkoutDayBuilder(WorkoutDayName name, List<WorkoutExercise> exercises) {
        this.id = new WorkoutDayId(UUID.randomUUID());
        this.name = name;
        this.exercises = exercises;
    }

    WorkoutDayBuilder(WorkoutDay workoutDay) {
        this.id = workoutDay.getId();
        this.name = workoutDay.getName();
        this.exercises = workoutDay.getExercises();
    }

    WorkoutDayBuilder withName(WorkoutDayName name) {
        this.name = name;
        return this;
    }

    WorkoutDayBuilder withExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    WorkoutDay build() {
        return new WorkoutDay(id, name, exercises);
    }
}
