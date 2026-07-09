package com.standofit.back.modules.training.execution.application.command.cancel_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cancel Session Handler Tests")
class CancelSessionHandlerTest {

  @Mock private CancelSessionService service;

  private CancelSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new CancelSessionHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command = new CancelSessionCommand(new SessionId(UUID.randomUUID()));

    handler.handle(command);

    verify(service, times(1)).cancel(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command = new CancelSessionCommand(new SessionId(UUID.randomUUID()));
    doThrow(new RuntimeException("Service error")).when(service).cancel(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
