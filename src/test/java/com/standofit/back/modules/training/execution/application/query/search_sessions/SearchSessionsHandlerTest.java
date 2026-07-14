package com.standofit.back.modules.training.execution.application.query.search_sessions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.ExerciseLogDto;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
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
@DisplayName("Search Sessions Handler Tests")
class SearchSessionsHandlerTest {

  @Mock private SessionReadRepository readRepository;

  private SearchSessionsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new SearchSessionsHandler(readRepository);
  }

  @Test
  @DisplayName("should return all sessions when query has no filters")
  void shouldReturnAllSessions() {
    var query = SearchSessionsQuery.all();
    var pagedResult = PagedResult.<SessionDto>of(List.of(), 0, 0, Integer.MAX_VALUE);
    when(readRepository.searchByCriteria(any(Criteria.class))).thenReturn(pagedResult);

    var result = handler.handle(query);

    assertNotNull(result);
    assertTrue(result.sessions().isEmpty());
    verify(readRepository, times(1)).searchByCriteria(any(Criteria.class));
  }

  @Test
  @DisplayName("should return single session when query has sessionId filter")
  void shouldReturnSingleSession() {
    var sessionId = new SessionId(UUID.randomUUID());
    var query = SearchSessionsQuery.byId(sessionId);
    var dto =
        new SessionDto(
            sessionId.value(),
            UUID.randomUUID(),
            "IN_PROGRESS",
            List.of(
                new ExerciseLogDto(
                    UUID.randomUUID(), UUID.randomUUID(), "Bench Press", "CHEST", 3, 10, 50)),
            "",
            "",
            "");
    var pagedResult = PagedResult.<SessionDto>of(List.of(dto), 1, 0, Integer.MAX_VALUE);
    when(readRepository.searchByCriteria(any(Criteria.class))).thenReturn(pagedResult);

    var result = handler.handle(query);

    assertNotNull(result);
    assertEquals(1, result.sessions().size());
    assertEquals(sessionId.value(), result.sessions().get(0).id());
    verify(readRepository, times(1)).searchByCriteria(any(Criteria.class));
  }
}
