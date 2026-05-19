package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
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

  @Mock private GetSessionByIdService service;

  private GetSessionByIdHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetSessionByIdHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var sessionId = new SessionId(UUID.randomUUID());
    var query = new GetSessionByIdQuery(sessionId);
    var dto = new SessionDto(sessionId.value(), UUID.randomUUID(), "IN_PROGRESS", List.of(), "", "", "");
    when(service.findById(query)).thenReturn(dto);

    var result = handler.handle(query);

    assertNotNull(result);
    verify(service, times(1)).findById(query);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var query = new GetSessionByIdQuery(new SessionId(UUID.randomUUID()));
    doThrow(new RuntimeException("Service error")).when(service).findById(query);

    assertThrows(RuntimeException.class, () -> handler.handle(query));
  }
}
