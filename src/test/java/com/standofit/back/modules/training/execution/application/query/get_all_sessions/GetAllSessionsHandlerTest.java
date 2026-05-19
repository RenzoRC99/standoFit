package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
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

  @Mock private GetAllSessionsService service;

  private GetAllSessionsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GetAllSessionsHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var query = new GetAllSessionsQuery();
    var dto = new SessionListDto(List.of());
    when(service.findAll(query)).thenReturn(dto);

    handler.handle(query);

    verify(service, times(1)).findAll(query);
  }
}
