package com.standofit.back.modules.training.execution.infrastructure.mapper;

import com.standofit.back.api.execution.dto.SessionDTO;
import com.standofit.back.api.execution.dto.SessionListDTO;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionStatus;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SessionDTOMapper {

  public SessionDTO toDTO(Session session) {
    return new SessionDTO()
        .id(session.getId().value())
        .dayId(session.getDayId().value())
        .status(mapStatus(session.getStatus()))
        .notes(session.getNotes().value())
        .createdAt(session.getCreatedAt().value().atOffset(ZoneOffset.UTC))
        .updatedAt(session.getUpdatedAt().value().atOffset(ZoneOffset.UTC));
  }

  public List<SessionDTO> toDTO(List<Session> sessions) {
    return sessions.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public SessionListDTO toListDTO(List<Session> sessions) {
    return new SessionListDTO().sessions(toDTO(sessions));
  }

  private SessionDTO.StatusEnum mapStatus(WorkoutSessionStatus status) {
    return SessionDTO.StatusEnum.valueOf(status.name());
  }
}
