package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
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
class GetAllSessionsHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private SessionDTOMapper mapper;

  private GetAllSessionsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetAllSessionsHandler(repository, mapper);
  }

  @Test
  void should_return_all_sessions() {
    Session session =
        Session.create(new SessionId(UUID.randomUUID()), new SessionDayId(UUID.randomUUID()));
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
    when(repository.getAll()).thenReturn(sessions);
    when(mapper.toListDTO(sessions)).thenReturn(dto);

    SessionListDto result = handler.handle(new GetAllSessionsQuery());

    verify(repository, times(1)).getAll();
    verify(mapper, times(1)).toListDTO(sessions);
  }
}
