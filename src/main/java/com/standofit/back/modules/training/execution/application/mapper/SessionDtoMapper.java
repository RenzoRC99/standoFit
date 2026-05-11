package com.standofit.back.modules.training.execution.application.mapper;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
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
        List.of(), // exercises - empty for now
        session.getNotes().value(),
        session.getCreatedAt().value().toString(),
        session.getUpdatedAt().value().toString());
  }

  public SessionListDto toListDto(List<Session> sessions) {
    return new SessionListDto(sessions.stream().map(this::toDto).toList());
  }
}
