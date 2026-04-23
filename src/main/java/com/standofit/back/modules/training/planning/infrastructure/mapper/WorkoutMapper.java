package com.standofit.back.modules.training.planning.infrastructure.mapper;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutDayJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutExerciseJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutMapper {

    public WorkoutJpaEntity toEntity(Workout workout) {
        Instant now = Instant.now();
        WorkoutJpaEntity entity =
                new WorkoutJpaEntity(
                        workout.getId().value(),
                        workout.getName().value(),
                        workout.getDescription() != null ? workout.getDescription().value() : "",
                        now,
                        now);

        int orderIndex = 0;
        for (WorkoutDay day : workout.getDays()) {
            entity.addDay(toEntity(day, orderIndex++));
        }

        return entity;
    }

    public WorkoutDayJpaEntity toEntity(WorkoutDay day, int orderIndex) {
        WorkoutDayJpaEntity entity =
                new WorkoutDayJpaEntity(day.getId().value(), day.getName().value(), orderIndex);

        for (WorkoutExercise exercise : day.getExercises()) {
            entity.addExercise(toEntity(exercise));
        }

        return entity;
    }

    public WorkoutExerciseJpaEntity toEntity(WorkoutExercise exercise) {
        return new WorkoutExerciseJpaEntity(
                exercise.getId().value(),
                exercise.getExerciseId().value(),
                exercise.getSets().value(),
                exercise.getReps().value(),
                exercise.getRestSeconds().value());
    }

    public Workout toDomain(WorkoutJpaEntity entity) {
        WorkoutName name = new WorkoutName(entity.getName());
        WorkoutDescription description =
                entity.getDescription() != null ? new WorkoutDescription(entity.getDescription()) : null;

        List<WorkoutDay> days =
                entity.getDays().stream().map(this::toDomain).collect(Collectors.toList());

        return Workout.create(new WorkoutId(entity.getId()), description, name, days);
    }

    public WorkoutDay toDomain(WorkoutDayJpaEntity entity) {
        WorkoutDayName name = new WorkoutDayName(entity.getName());

        List<WorkoutExercise> exercises =
                entity.getExercises().stream().map(this::toDomain).collect(Collectors.toList());

        return WorkoutDay.create(new WorkoutDayId(entity.getId()), name, exercises);
    }

    public WorkoutExercise toDomain(WorkoutExerciseJpaEntity entity) {
        return WorkoutExercise.create(
                new WorkoutExerciseId(entity.getId()),
                new ExerciseId(entity.getExerciseId()),
                new WorkoutExerciseSets(entity.getSets()),
                new WorkoutExerciseReps(entity.getReps()),
                new WorkoutExerciseRest(entity.getRestSeconds()));
    }
}
