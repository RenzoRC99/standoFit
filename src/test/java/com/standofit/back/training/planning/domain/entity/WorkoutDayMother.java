package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.List;

public final class WorkoutDayMother {

    private WorkoutDayMother() {}

    public static WorkoutDayName aWorkoutDayName() {
        return new WorkoutDayName("Day 1 - Upper Body");
    }

    public static WorkoutDay aWorkoutDay() {
        return WorkoutDay.create(
                aWorkoutDayName(),
                List.of(WorkoutExerciseMother.aWorkoutExercise())
        );
    }

    public static WorkoutDay aWorkoutDay(WorkoutDayName name) {
        return WorkoutDay.create(name, List.of(WorkoutExerciseMother.aWorkoutExercise()));
    }

    public static WorkoutDay aWorkoutDayWithExercises(List<WorkoutExercise> exercises) {
        return WorkoutDay.create(aWorkoutDayName(), exercises);
    }

    public static WorkoutDay aWorkoutDayWithoutExercises(String name) {
        return WorkoutDay.create(new WorkoutDayName(name), List.of());
    }

    public static WorkoutDay aWorkoutDayWithoutExercises() {
        return WorkoutDay.create(aWorkoutDayName(), List.of());
    }
}
