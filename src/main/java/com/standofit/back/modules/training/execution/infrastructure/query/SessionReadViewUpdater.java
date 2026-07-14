package com.standofit.back.modules.training.execution.infrastructure.query;

import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.ExerciseLogReadViewJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.readview.SessionReadViewJpaEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SessionReadViewUpdater {

  private final SessionReadViewJpaRepository readViewRepository;
  private final ExerciseRepository exerciseRepository;

  public SessionReadViewUpdater(
      SessionReadViewJpaRepository readViewRepository, ExerciseRepository exerciseRepository) {
    this.readViewRepository = readViewRepository;
    this.exerciseRepository = exerciseRepository;
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
    Map<UUID, Exercise> exerciseMap = loadExercises(session.getLogs());

    var entity =
        new SessionReadViewJpaEntity(
            session.getId().value(),
            session.getDayId().value(),
            session.getStatus().name(),
            session.getNotes().value(),
            session.getCreatedAt().value(),
            session.getUpdatedAt().value());

    for (ExerciseLog log : session.getLogs()) {
      Exercise exercise = exerciseMap.get(log.getExerciseId().value());
      if (exercise == null) {
        throw new SessionInfrastructureException(
            "Exercise not found in catalog: " + log.getExerciseId().value());
      }
      entity.addLog(
          new ExerciseLogReadViewJpaEntity(
              log.getId().value(),
              log.getExerciseId().value(),
              exercise.getName(),
              exercise.getMuscleGroup().name(),
              log.getSets().value(),
              log.getReps().value(),
              log.getWeight().value()));
    }

    return entity;
  }

  private Map<UUID, Exercise> loadExercises(List<ExerciseLog> logs) {
    Set<String> stringIds =
        logs.stream()
            .map(log -> log.getExerciseId().value().toString())
            .collect(Collectors.toSet());
    if (stringIds.isEmpty()) {
      return Map.of();
    }
    return exerciseRepository.findAllById(stringIds).stream()
        .collect(Collectors.toMap(ex -> UUID.fromString(ex.getId()), ex -> ex));
  }
}
