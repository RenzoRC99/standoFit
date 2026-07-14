package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.ExerciseLogReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaSessionReadMapper {

  public SessionDto toDto(SessionReadViewJpaEntity entity) {
    return new SessionDto(
        entity.getId(),
        entity.getDayId(),
        entity.getStatus(),
        entity.getLogs().stream().map(this::toExerciseDto).toList(),
        entity.getNotes(),
        entity.getCreatedAt().toString(),
        entity.getUpdatedAt().toString());
  }

  public ExerciseLogDto toExerciseDto(ExerciseLogReadViewJpaEntity entity) {
    return new ExerciseLogDto(
        entity.getId(),
        entity.getExerciseId(),
        entity.getExerciseName(),
        entity.getMuscleGroup(),
        entity.getSets(),
        entity.getReps(),
        entity.getWeight());
  }
}
