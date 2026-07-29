package com.standofit.back.modules.training.execution.presentation;

import com.standofit.back.api.execution.dto.ExerciseLogDTO;
import com.standofit.back.api.execution.dto.PlannedExerciseDTO;
import com.standofit.back.api.execution.dto.SessionDTO;
import com.standofit.back.api.execution.dto.SessionPageDTO;
import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.PlannedExerciseDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.shared.domain.criteria.PagedResult;

public class SessionApiMapper {

  public static SessionDTO toApi(SessionDto dto) {
    var result =
        new SessionDTO()
            .id(dto.id())
            .dayId(dto.dayId())
            .workoutName(dto.workoutName())
            .dayName(dto.dayName())
            .status(SessionDTO.StatusEnum.valueOf(dto.status()))
            .notes(dto.notes())
            .durationMinutes(dto.durationMinutes())
            .totalVolume(dto.totalVolume())
            .totalExercises(dto.totalExercises())
            .totalSets(dto.totalSets())
            .startedAt(dto.startedAt())
            .finishedAt(dto.finishedAt());

    if (dto.exercises() != null) {
      result.logs(dto.exercises().stream().map(SessionApiMapper::toApi).toList());
    }
    if (dto.plannedExercises() != null) {
      result.plannedExercises(
          dto.plannedExercises().stream().map(SessionApiMapper::toApi).toList());
    }

    return result;
  }

  public static ExerciseLogDTO toApi(ExerciseLogDto dto) {
    return new ExerciseLogDTO()
        .id(dto.id())
        .exerciseId(dto.exerciseId())
        .exerciseName(dto.exerciseName())
        .muscleGroup(dto.muscleGroup())
        .sets(dto.sets())
        .reps(dto.reps())
        .weight(dto.weight());
  }

  public static PlannedExerciseDTO toApi(PlannedExerciseDto dto) {
    return new PlannedExerciseDTO()
        .exerciseId(dto.exerciseId())
        .exerciseName(dto.exerciseName())
        .muscleGroup(dto.muscleGroup())
        .sets(dto.sets())
        .reps(dto.reps())
        .restSeconds(dto.restSeconds());
  }

  public static SessionPageDTO toApi(PagedResult<SessionDto> result) {
    return new SessionPageDTO()
        .content(result.items().stream().map(SessionApiMapper::toApi).toList())
        .pageNumber(result.page())
        .pageSize(result.pageSize())
        .totalElements((int) result.total())
        .totalPages(result.totalPages());
  }
}
