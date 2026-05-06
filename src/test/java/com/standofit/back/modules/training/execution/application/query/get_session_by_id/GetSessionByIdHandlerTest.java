package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSessionByIdHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private SessionDTOMapper mapper;

  private GetSessionByIdHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetSessionByIdHandler(repository, mapper);
  }

  @Test
  void should_return_session_when_exists() {
    SessionId sessionId = new SessionId(UUID.randomUUID());
    Session session = Session.create(sessionId, new SessionDayId(UUID.randomUUID()));
    SessionDto dto = new SessionDto(
        session.getId().value(),
        session.getDayId().value(),
        session.getStatus().name(),
        List.of(),
        "",
        "",
        "");
    when(repository.findById(sessionId)).thenReturn(session);
    when(mapper.toDTO(session)).thenReturn(dto);

    SessionDto result = handler.handle(new GetSessionByIdQuery(sessionId));

    assertNotNull(result);
    verify(repository, times(1)).findById(sessionId);
    verify(mapper, times(1)).toDTO(session);
  }

  @Test
  void should_throw_when_session_not_found() {
    SessionId sessionId = new SessionId(UUID.randomUUID());
    when(repository.findById(sessionId)).thenReturn(null);

    assertThrows(
        IllegalArgumentException.class,
        () -> handler.handle(new GetSessionByIdQuery(sessionId)));
  }
}