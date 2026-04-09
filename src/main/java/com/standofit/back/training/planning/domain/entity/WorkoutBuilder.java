package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import com.standofit.back.training.planning.domain.vo.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

final class WorkoutBuilder {
    private final WorkoutId id;
    private WorkoutName name;
    private WorkoutDescription description;
    private List<WorkoutDay> days;
    private WorkoutVersion version;
    private final WorkoutCreatedAt createdAt;

     WorkoutBuilder(WorkoutDescription description, WorkoutName name, WorkoutVersion version, List<WorkoutDay> days) {
        this.id = new WorkoutId(UUID.randomUUID());
        this.description = description;
        this.name = name;
        this.version = version;
        this.days = days;
        this.createdAt = new WorkoutCreatedAt(Instant.now());
    }

    WorkoutBuilder(Workout workout){
         this.id = workout.getId();
         this.name = workout.getName();
         this.description = workout.getDescription();
         this.version = workout.getVersion();
         this.days = workout.getDays();
         this.createdAt = workout.getCreatedAt();
    }

    WorkoutBuilder withName(WorkoutName name) {
        this.name = name;
        return this;
    }

    WorkoutBuilder withDescription(WorkoutDescription description) {
        this.description = description;
        return this;
    }

    WorkoutBuilder withDays(List<WorkoutDay> days) {
        this.days = days;
        return this;
    }

    WorkoutBuilder withVersion(WorkoutVersion version) {
        this.version = version;
        return this;
    }

    Workout build() {
        WorkoutUpdatedAt finalUpdatedAt = new WorkoutUpdatedAt(Instant.now());
        return new Workout(
                id,
                name,
                description,
                days,
                version,
                createdAt,
                finalUpdatedAt
        );
    }
}
