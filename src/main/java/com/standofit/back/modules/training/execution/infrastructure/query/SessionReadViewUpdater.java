package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.training.execution.domain.catalog.ExerciseResolver;
import com.standofit.back.modules.training.execution.domain.catalog.WorkoutResolver;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.ExerciseLogReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.PlannedExerciseReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SessionReadViewUpdater {

  private final SessionReadViewJpaRepository readViewRepository;
  private final ExerciseResolver exerciseResolver;
  private final WorkoutResolver workoutResolver;

  public SessionReadViewUpdater(
      SessionReadViewJpaRepository readViewRepository,
      ExerciseResolver exerciseResolver,
      WorkoutResolver workoutResolver) {
    this.readViewRepository = readViewRepository;
    this.exerciseResolver = exerciseResolver;
    this.workoutResolver = workoutResolver;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void upsert(Session session) {
    readViewRepository
        .findById(session.getId().value())
        .ifPresent(entity -> readViewRepository.delete(entity));
    readViewRepository.saveAndFlush(toEntity(session));
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void remove(UUID sessionId) {
    readViewRepository.findById(sessionId).ifPresent(entity -> readViewRepository.delete(entity));
  }

  private SessionReadViewJpaEntity toEntity(Session session) {
    Map<UUID, ExerciseResolver.ExerciseInfo> exerciseMap = loadExercises(session.getLogs());

    Optional<WorkoutResolver.WorkoutInfo> workoutInfoOpt =
        workoutResolver.resolveByDayId(session.getDayId().value());

    String workoutName = workoutInfoOpt.map(WorkoutResolver.WorkoutInfo::workoutName).orElse(null);
    String dayName = workoutInfoOpt.map(WorkoutResolver.WorkoutInfo::dayName).orElse(null);

    Integer durationMinutes = null;
    if (session.getCreatedAt() != null && session.getUpdatedAt() != null) {
      durationMinutes =
          (int)
              Duration.between(session.getCreatedAt().value(), session.getUpdatedAt().value())
                  .toMinutes();
    }

    var entity =
        new SessionReadViewJpaEntity(
            session.getId().value(),
            session.getDayId().value(),
            workoutName,
            dayName,
            session.getStatus().name(),
            session.getNotes().value(),
            durationMinutes,
            session.getCreatedAt().value(),
            session.getUpdatedAt().value());

    for (ExerciseLog log : session.getLogs()) {
      ExerciseResolver.ExerciseInfo info = exerciseMap.get(log.getExerciseId().value());
      if (info == null) {
        throw new SessionInfrastructureException(
            "Exercise not found in catalog: " + log.getExerciseId().value());
      }
      entity.addLog(
          new ExerciseLogReadViewJpaEntity(
              log.getId().value(),
              log.getExerciseId().value(),
              info.name(),
              info.muscleGroup(),
              log.getSets().value(),
              log.getReps().value(),
              log.getWeight().value()));
    }

    workoutInfoOpt.ifPresent(
        info -> {
          int idx = 0;
          for (var pe : info.plannedExercises()) {
            entity.addPlannedExercise(
                new PlannedExerciseReadViewJpaEntity(
                    UUID.randomUUID(),
                    pe.exerciseId(),
                    pe.sets(),
                    pe.reps(),
                    pe.restSeconds(),
                    idx++));
          }
        });

    return entity;
  }

  private Map<UUID, ExerciseResolver.ExerciseInfo> loadExercises(List<ExerciseLog> logs) {
    Set<UUID> ids =
        logs.stream().map(log -> log.getExerciseId().value()).collect(Collectors.toSet());
    if (ids.isEmpty()) {
      return Map.of();
    }
    return exerciseResolver.resolve(ids);
  }
}
