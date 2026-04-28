package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.modules.training.execution.infrastructure.entity.ExerciseLogJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.entity.SessionJpaEntity;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

  public SessionJpaEntity toEntity(Session session) {
    SessionJpaEntity entity =
        new SessionJpaEntity(
            session.getId().value(),
            session.getDayId().value(),
            session.getStatus().name(),
            session.getNotes().value(),
            session.getCreatedAt().value(),
            session.getUpdatedAt().value());

    for (ExerciseLog log : session.getLogs()) {
      entity.addLog(toEntity(log));
    }

    return entity;
  }

  public ExerciseLogJpaEntity toEntity(ExerciseLog log) {
    return new ExerciseLogJpaEntity(
        log.getId().value(),
        log.getExerciseId().value(),
        log.getSets().value(),
        log.getReps().value(),
        log.getWeight().value());
  }

  public Session toDomain(SessionJpaEntity entity) {
    List<ExerciseLog> logs =
        entity.getLogs().stream().map(this::toDomain).collect(Collectors.toList());

    return Session.copy(
        new SessionId(entity.getId()),
        new SessionDayId(entity.getDayId()),
        WorkoutSessionStatus.valueOf(entity.getStatus()),
        logs,
        new WorkoutSessionNotes(entity.getNotes()),
        new SessionCreatedAt(entity.getCreatedAt()),
        new SessionUpdatedAt(entity.getUpdatedAt()));
  }

  public ExerciseLog toDomain(ExerciseLogJpaEntity entity) {
    return ExerciseLog.create(
        new ExerciseLogId(entity.getId()),
        new ExerciseId(entity.getExerciseId()),
        new ExerciseLogSets(entity.getSets()),
        new ExerciseLogReps(entity.getReps()),
        new ExerciseLogWeight(entity.getWeight()));
  }
}
