package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.PlannedExerciseDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.domain.catalog.ExerciseResolver;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.ExerciseLogReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.PlannedExerciseReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class JpaSessionReadMapper {

  private final ExerciseResolver exerciseResolver;

  public JpaSessionReadMapper(ExerciseResolver exerciseResolver) {
    this.exerciseResolver = exerciseResolver;
  }

  public SessionDto toDto(SessionReadViewJpaEntity entity) {
    Map<UUID, ExerciseResolver.ExerciseInfo> exerciseMap = loadExercises(entity);

    List<ExerciseLogDto> logs =
        entity.getLogs().stream().map(log -> toExerciseDto(log, exerciseMap)).toList();

    List<PlannedExerciseDto> planned =
        entity.getPlannedExercises().stream().map(pe -> toPlannedDto(pe, exerciseMap)).toList();

    int totalVolume = logs.stream().mapToInt(log -> log.sets() * log.reps() * log.weight()).sum();

    int totalSets = logs.stream().mapToInt(ExerciseLogDto::sets).sum();

    int totalExercises = (int) logs.stream().map(ExerciseLogDto::exerciseId).distinct().count();

    return new SessionDto(
        entity.getId(),
        entity.getDayId(),
        entity.getWorkoutName(),
        entity.getDayName(),
        entity.getStatus(),
        logs,
        planned,
        entity.getNotes(),
        entity.getDurationMinutes(),
        totalVolume,
        totalExercises,
        totalSets,
        entity.getCreatedAt().toString(),
        entity.getUpdatedAt().toString());
  }

  private ExerciseLogDto toExerciseDto(
      ExerciseLogReadViewJpaEntity entity, Map<UUID, ExerciseResolver.ExerciseInfo> exerciseMap) {
    ExerciseResolver.ExerciseInfo info = exerciseMap.get(entity.getExerciseId());
    return new ExerciseLogDto(
        entity.getId(),
        entity.getExerciseId(),
        info != null ? info.name() : entity.getExerciseName(),
        info != null ? info.muscleGroup() : entity.getMuscleGroup(),
        entity.getSets(),
        entity.getReps(),
        entity.getWeight());
  }

  private PlannedExerciseDto toPlannedDto(
      PlannedExerciseReadViewJpaEntity entity,
      Map<UUID, ExerciseResolver.ExerciseInfo> exerciseMap) {
    ExerciseResolver.ExerciseInfo info = exerciseMap.get(entity.getExerciseId());
    return new PlannedExerciseDto(
        entity.getExerciseId(),
        info != null ? info.name() : null,
        info != null ? info.muscleGroup() : null,
        entity.getSets(),
        entity.getReps(),
        entity.getRestSeconds());
  }

  private Map<UUID, ExerciseResolver.ExerciseInfo> loadExercises(SessionReadViewJpaEntity entity) {
    Set<UUID> ids = new HashSet<>();

    for (var log : entity.getLogs()) {
      ids.add(log.getExerciseId());
    }
    for (var pe : entity.getPlannedExercises()) {
      ids.add(pe.getExerciseId());
    }

    if (ids.isEmpty()) {
      return Map.of();
    }

    return exerciseResolver.resolve(ids);
  }
}
