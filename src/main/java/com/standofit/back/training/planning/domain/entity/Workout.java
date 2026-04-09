package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import com.standofit.back.training.planning.domain.vo.*;

import java.util.List;

public class Workout extends AggregateRoot {
    private final WorkoutId id;
    private final WorkoutName name;
    private final WorkoutDescription description;
    private final List<WorkoutDay> days;
    private final WorkoutVersion version;
    private final WorkoutCreatedAt createdAt;
    private final WorkoutUpdatedAt updatedAt;

    Workout(WorkoutId id, WorkoutName name, WorkoutDescription description,
                   List<WorkoutDay> days, WorkoutVersion version,
                   WorkoutCreatedAt createdAt, WorkoutUpdatedAt updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.days = days;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public WorkoutId getId() { return id; }
    public WorkoutName getName() { return name; }
    public WorkoutDescription getDescription() { return description; }
    public List<WorkoutDay> getDays() { return days; }
    public WorkoutVersion getVersion() { return version; }
    public WorkoutCreatedAt getCreatedAt() { return createdAt; }
    public WorkoutUpdatedAt getUpdatedAt() { return updatedAt;}

    public static Workout create(
            WorkoutDescription description,
            WorkoutName name,
            WorkoutVersion version,
            List<WorkoutDay> days
    ) {
        return new WorkoutBuilder
                (description, name, version, days)
                .build();
    }

    public Workout renameWorkout(WorkoutName name) {
        return new WorkoutBuilder(this)
                .withName(name)
                .build();
    }
}
