package com.standofit.back.modules.training.execution.application.mapper;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SessionDtoMapper {

  public SessionDto toDto(Session session) {
    return new SessionDto(
        session.getId().value(),
        session.getDayId().value(),
        session.getStatus().name(),
        session.getLogs().stream().map(this::toExerciseDto).toList(),
        session.getNotes().value(),
        session.getCreatedAt().value().toString(),
        session.getUpdatedAt().value().toString());
  }

  private ExerciseLogDto toExerciseDto(ExerciseLog log) {
    return new ExerciseLogDto(
        log.getId().value(),
        log.getExerciseId().value(),
        log.getSets().value(),
        log.getReps().value(),
        log.getWeight().value());
  }

  public SessionListDto toListDto(List<Session> sessions) {
    return new SessionListDto(sessions.stream().map(this::toDto).toList());
  }
}
