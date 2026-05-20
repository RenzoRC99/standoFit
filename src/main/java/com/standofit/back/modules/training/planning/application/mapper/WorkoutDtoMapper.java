package com.standofit.back.modules.training.planning.application.mapper;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDayDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutExerciseDto;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class WorkoutDtoMapper {

  public WorkoutDto toDto(Workout workout) {
    return toDto(workout, Collections.emptyMap(), Collections.emptyMap());
  }

  public WorkoutDto toDto(
      Workout workout,
      Map<UUID, String> exerciseNames,
      Map<UUID, String> exerciseMuscleGroups) {
    List<WorkoutDayDto> days =
        workout.getDays().stream()
            .map(day -> toDto(day, exerciseNames, exerciseMuscleGroups))
            .collect(Collectors.toList());

    return new WorkoutDto(
        workout.getId().value(),
        workout.getName().value(),
        workout.getDescription().value(),
        days,
        workout.getCreatedAt().value().toString(),
        workout.getUpdatedAt().value().toString());
  }

  public WorkoutDayDto toDto(WorkoutDay day) {
    return toDto(day, Collections.emptyMap(), Collections.emptyMap());
  }

  public WorkoutDayDto toDto(
      WorkoutDay day,
      Map<UUID, String> exerciseNames,
      Map<UUID, String> exerciseMuscleGroups) {
    List<WorkoutExerciseDto> exercises =
        day.getExercises().stream()
            .map(ex -> toDto(ex, exerciseNames, exerciseMuscleGroups))
            .collect(Collectors.toList());

    return new WorkoutDayDto(day.getId().value(), day.getName().value(), exercises);
  }

  public WorkoutExerciseDto toDto(WorkoutExercise exercise) {
    return toDto(exercise, Collections.emptyMap(), Collections.emptyMap());
  }

  public WorkoutExerciseDto toDto(
      WorkoutExercise exercise,
      Map<UUID, String> exerciseNames,
      Map<UUID, String> exerciseMuscleGroups) {
    UUID exId = exercise.getExerciseId().value();
    return new WorkoutExerciseDto(
        exercise.getId().value(),
        exId,
        exerciseNames.getOrDefault(exId, null),
        exerciseMuscleGroups.getOrDefault(exId, null),
        exercise.getSets().value(),
        exercise.getReps().value(),
        exercise.getRestSeconds().value());
  }
}
