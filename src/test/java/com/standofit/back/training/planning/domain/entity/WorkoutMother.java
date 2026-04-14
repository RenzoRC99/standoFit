package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.training.planning.domain.vo.WorkoutName;

import java.util.ArrayList;
import java.util.List;

public final class WorkoutMother {

    private WorkoutMother() {}

    public static WorkoutName aWorkoutName() {
        return new WorkoutName("Full Body Workout");
    }

    public static WorkoutDescription aWorkoutDescription() {
        return new WorkoutDescription("Complete workout for all muscle groups");
    }

    public static Workout aWorkout() {
        return Workout.create(
                aWorkoutDescription(),
                aWorkoutName(),
                List.of(WorkoutDayMother.aWorkoutDay())
        );
    }

    public static Workout aWorkoutWithName(String name) {
        return Workout.create(
                aWorkoutDescription(),
                new WorkoutName(name),
                List.of(WorkoutDayMother.aWorkoutDay())
        );
    }

    public static Workout aWorkoutWithDays(List<WorkoutDay> days) {
        return Workout.create(aWorkoutDescription(), aWorkoutName(), days);
    }

    public static void aWorkoutWithNoDays() {
        Workout.create(
                aWorkoutDescription(),
                aWorkoutName(),
                new ArrayList<>()
        );
    }
}
