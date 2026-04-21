package com.standofit.back.training.planning.application.mapper;

import com.standofit.back.training.planning.application.dto.WorkoutDayDto;
import com.standofit.back.training.planning.application.dto.WorkoutDto;
import com.standofit.back.training.planning.application.dto.WorkoutExerciseDto;
import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.training.planning.domain.entity.WorkoutExercise;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutDtoMapper {

    public WorkoutDto toDto(Workout workout) {
        List<WorkoutDayDto> days = workout.getDays().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new WorkoutDto(
                workout.getId().value(),
                workout.getName().value(),
                workout.getDescription() != null ? workout.getDescription().value() : null,
                days,
                workout.getCreatedAt().value().toString(),
                workout.getUpdatedAt().value().toString()
        );
    }

    public WorkoutDayDto toDto(WorkoutDay day) {
        List<WorkoutExerciseDto> exercises = day.getExercises().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new WorkoutDayDto(
                day.getId().value(),
                day.getName().value(),
                exercises
        );
    }

    public WorkoutExerciseDto toDto(WorkoutExercise exercise) {
        return new WorkoutExerciseDto(
                exercise.getId().value(),
                exercise.getExerciseId().value(),
                exercise.getSets().value(),
                exercise.getReps().value(),
                exercise.getRestSeconds().value()
        );
    }
}