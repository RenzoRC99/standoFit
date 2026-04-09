package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;

import java.util.List;

public class WorkoutDay {
    private final WorkoutDayId id;
    private final WorkoutDayName name;
    private final List<WorkoutExercise> exercises;

    public WorkoutDay(WorkoutDayId id, WorkoutDayName name, List<WorkoutExercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }
}
