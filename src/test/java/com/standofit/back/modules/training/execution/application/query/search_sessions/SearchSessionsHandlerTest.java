package com.standofit.back.modules.training.execution.application.query.search_sessions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Search Sessions Handler Tests")
class SearchSessionsHandlerTest {

  @Mock private SessionReadRepository readRepository;

  private SearchSessionsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new SearchSessionsHandler(readRepository);
  }

  @Test
  @DisplayName("should return all sessions when query has no sessionId")
  void shouldReturnAllSessions() {
    var query = SearchSessionsQuery.all();
    var dto = new SessionListDto(List.of());
    when(readRepository.findAllAsDtos()).thenReturn(dto);

    var result = handler.handle(query);

    assertNotNull(result);
    verify(readRepository, times(1)).findAllAsDtos();
    verify(readRepository, never()).findDtoById(any());
  }

  @Test
  @DisplayName("should return single session when query has sessionId")
  void shouldReturnSingleSession() {
    var sessionId = new SessionId(UUID.randomUUID());
    var query = SearchSessionsQuery.byId(sessionId);
    var dto =
        new SessionDto(
            sessionId.value(),
            UUID.randomUUID(),
            "IN_PROGRESS",
            List.of(new ExerciseLogDto(UUID.randomUUID(), UUID.randomUUID(), 3, 10, 50)),
            "",
            "",
            "");
    when(readRepository.findDtoById(sessionId.value())).thenReturn(Optional.of(dto));

    var result = handler.handle(query);

    assertNotNull(result);
    assertEquals(1, result.sessions().size());
    assertEquals(sessionId.value(), result.sessions().get(0).id());
    verify(readRepository, times(1)).findDtoById(sessionId.value());
  }

  @Test
  @DisplayName("should throw when session not found by id")
  void shouldThrowWhenNotFound() {
    var sessionId = new SessionId(UUID.randomUUID());
    var query = SearchSessionsQuery.byId(sessionId);
    when(readRepository.findDtoById(sessionId.value())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> handler.handle(query));
  }
}
