package com.standofit.back.modules.training.execution.application.command.delete_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Delete Session Handler Tests")
class DeleteSessionHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private ApplicationEventBus eventBus;

  private DeleteSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new DeleteSessionHandler(repository, eventBus);
  }

  @Test
  @DisplayName("should delete session and publish success event")
  void shouldDeleteSession() {
    var sessionId = new SessionId(UUID.randomUUID());
    var command = new DeleteSessionCommand(sessionId);

    handler.execute(command);

    verify(repository, times(1)).deleteById(sessionId);
    verify(eventBus, times(1)).publish(any());
  }

  @Test
  @DisplayName("should publish failure event and propagate exception")
  void shouldPublishFailureAndPropagate() {
    var sessionId = new SessionId(UUID.randomUUID());
    var command = new DeleteSessionCommand(sessionId);
    doThrow(new RuntimeException("DB error")).when(repository).deleteById(sessionId);

    assertThrows(RuntimeException.class, () -> handler.execute(command));
    verify(eventBus, times(1)).publish(any());
  }
}
