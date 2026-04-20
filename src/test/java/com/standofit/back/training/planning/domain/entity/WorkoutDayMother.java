package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.List;
import java.util.UUID;

public final class WorkoutDayMother {

    private WorkoutDayMother() {}

    public static WorkoutDayId aWorkoutDayId() {
        return new WorkoutDayId(UUID.randomUUID());
    }

    public static WorkoutDayName aWorkoutDayName() {
        return new WorkoutDayName("Day 1 - Upper Body");
    }

    public static WorkoutDay aWorkoutDay() {
        return WorkoutDay.create(
                aWorkoutDayId(),
                aWorkoutDayName(),
                List.of(WorkoutExerciseMother.aWorkoutExercise())
        );
    }

    public static WorkoutDay aWorkoutDay(WorkoutDayName name) {
        return WorkoutDay.create(aWorkoutDayId(), name, List.of(WorkoutExerciseMother.aWorkoutExercise()));
    }

    public static WorkoutDay aWorkoutDayWithExercises(List<WorkoutExercise> exercises) {
        return WorkoutDay.create(aWorkoutDayId(), aWorkoutDayName(), exercises);
    }

    public static WorkoutDay aWorkoutDayWithoutExercises(String name) {
        return WorkoutDay.create(aWorkoutDayId(), new WorkoutDayName(name), null);
    }

    public static WorkoutDay aWorkoutDayWithoutExercises() {
        return WorkoutDay.create(aWorkoutDayId(), aWorkoutDayName(), null);
    }
}
