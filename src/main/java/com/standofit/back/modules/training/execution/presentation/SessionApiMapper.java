package com.standofit.back.modules.training.execution.presentation;

import com.standofit.back.api.execution.dto.ExerciseLogDTO;
import com.standofit.back.api.execution.dto.SessionDTO;
import com.standofit.back.api.execution.dto.SessionListDTO;
import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;

public class SessionApiMapper {

  public static SessionDTO toApi(SessionDto dto) {
    return new SessionDTO()
        .id(dto.id())
        .dayId(dto.dayId())
        .status(SessionDTO.StatusEnum.valueOf(dto.status()))
        .notes(dto.notes())
        .logs(dto.exercises().stream().map(SessionApiMapper::toApi).toList())
        .startedAt(dto.startedAt())
        .finishedAt(dto.finishedAt());
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

  public static SessionListDTO toApi(SessionListDto dto) {
    return new SessionListDTO()
        .sessions(dto.sessions().stream().map(SessionApiMapper::toApi).toList());
  }
}
