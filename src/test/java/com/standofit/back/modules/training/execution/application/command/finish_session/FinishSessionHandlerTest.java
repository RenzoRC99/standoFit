package com.standofit.back.modules.training.execution.application.command.finish_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Finish Session Handler Tests")
class FinishSessionHandlerTest {

  @Mock private FinishSessionService service;

  private FinishSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new FinishSessionHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new FinishSessionCommand(
            new com.standofit.back.shared.domain.valueobjects.ids.SessionId(
                java.util.UUID.randomUUID()));

    handler.handle(command);

    verify(service, times(1)).finish(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new FinishSessionCommand(
            new com.standofit.back.shared.domain.valueobjects.ids.SessionId(
                java.util.UUID.randomUUID()));
    doThrow(new RuntimeException("Service error")).when(service).finish(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
