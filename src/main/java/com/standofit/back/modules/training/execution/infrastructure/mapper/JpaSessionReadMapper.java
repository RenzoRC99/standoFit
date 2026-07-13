package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.infrastructure.entity.ExerciseLogJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.SessionJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaSessionReadMapper {

  public SessionDto toDto(SessionJpaEntity entity) {
    return new SessionDto(
        entity.getId(),
        entity.getDayId(),
        entity.getStatus(),
        entity.getLogs().stream().map(this::toExerciseDto).toList(),
        entity.getNotes(),
        entity.getCreatedAt().toString(),
        entity.getUpdatedAt().toString());
  }

  public ExerciseLogDto toExerciseDto(ExerciseLogJpaEntity entity) {
    return new ExerciseLogDto(
        entity.getId(),
        entity.getExerciseId(),
        entity.getSets(),
        entity.getReps(),
        entity.getWeight());
  }
}
