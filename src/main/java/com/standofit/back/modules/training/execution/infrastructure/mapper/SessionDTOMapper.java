package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SessionDTOMapper {

  public SessionDto toDTO(Session session) {
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

  public List<SessionDto> toDTO(List<Session> sessions) {
    return sessions.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public SessionListDto toListDTO(List<Session> sessions) {
    return new SessionListDto(toDTO(sessions));
  }
}
