package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.training.execution.domain.entity.SessionMother;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get All Sessions Handler Tests")
class GetAllSessionsHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private SessionDTOMapper mapper;

  private GetAllSessionsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetAllSessionsHandler(repository, mapper);
  }

  @Test
  @DisplayName("should return all sessions")
  void shouldReturnAllSessions() {
    Session session = SessionMother.aSession();
    List<Session> sessions = List.of(session);
    SessionDto sessionDto =
        new SessionDto(
            session.getId().value(),
            session.getDayId().value(),
            session.getStatus().name(),
            List.of(),
            "",
            "",
            "");
    SessionListDto dto = new SessionListDto(List.of(sessionDto));
    when(repository.findAll()).thenReturn(sessions);
    when(mapper.toListDTO(sessions)).thenReturn(dto);

    SessionListDto result = handler.handle(new GetAllSessionsQuery());

    verify(repository, times(1)).findAll();
    verify(mapper, times(1)).toListDTO(sessions);
  }
}
