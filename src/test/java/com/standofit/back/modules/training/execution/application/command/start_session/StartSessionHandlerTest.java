package com.standofit.back.modules.training.execution.application.command.start_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Start Session Handler Tests")
class StartSessionHandlerTest {

  @Mock private StartSessionService service;

  private StartSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new StartSessionHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command = new StartSessionCommand(new SessionDayId(UUID.randomUUID()));
    when(service.startSession(command)).thenReturn(UUID.randomUUID());

    handler.handle(command);

    verify(service, times(1)).startSession(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command = new StartSessionCommand(new SessionDayId(UUID.randomUUID()));
    doThrow(new RuntimeException("Service error")).when(service).startSession(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
