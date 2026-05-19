package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionInfrastructureException;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.training.execution.domain.entity.SessionMother;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get Session By Id Handler Tests")
class GetSessionByIdHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private SessionDTOMapper mapper;

  private GetSessionByIdHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetSessionByIdHandler(repository, mapper);
  }

  @Test
  @DisplayName("should return session when exists")
  void shouldReturnSessionWhenExists() {
    SessionId sessionId = SessionMother.aSessionId();
    Session session = SessionMother.aSessionWithId(sessionId);
    SessionDto dto =
        new SessionDto(
            session.getId().value(),
            session.getDayId().value(),
            session.getStatus().name(),
            List.of(),
            "",
            "",
            "");
    when(repository.getById(sessionId)).thenReturn(session);
    when(mapper.toDTO(session)).thenReturn(dto);

    SessionDto result = handler.handle(new GetSessionByIdQuery(sessionId));

    assertNotNull(result);
    verify(repository, times(1)).getById(sessionId);
    verify(mapper, times(1)).toDTO(session);
  }

  @Test
  @DisplayName("should throw when session not found")
  void shouldThrowWhenSessionNotFound() {
    SessionId sessionId = new SessionId(UUID.randomUUID());
    when(repository.getById(sessionId)).thenThrow(new SessionInfrastructureException("Session not found"));

    assertThrows(
        SessionInfrastructureException.class, () -> handler.handle(new GetSessionByIdQuery(sessionId)));
  }
}
