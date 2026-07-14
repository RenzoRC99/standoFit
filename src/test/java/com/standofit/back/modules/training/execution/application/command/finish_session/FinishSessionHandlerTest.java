package com.standofit.back.modules.training.execution.application.command.finish_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Finish Session Handler Tests")
class FinishSessionHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private SessionReadViewUpdater readViewUpdater;
  @Mock private ApplicationEventBus eventBus;

  private FinishSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new FinishSessionHandler(repository, readViewUpdater, eventBus);
  }

  @Test
  @DisplayName("should finish session, save and update read view on success")
  void shouldFinishAndSave() {
    var sessionId = new SessionId(UUID.randomUUID());
    var command = new FinishSessionCommand(sessionId);
    var session = Session.create(sessionId, new SessionDayId(UUID.randomUUID()));
    when(repository.getById(sessionId)).thenReturn(session);
    when(repository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));

    handler.execute(command);

    verify(repository, times(1)).getById(sessionId);
    verify(repository, times(1)).save(any(Session.class));
    verify(readViewUpdater, times(1)).upsert(any(Session.class));
    verify(eventBus, times(1)).publish(any());
  }

  @Test
  @DisplayName("should publish failure event and propagate exception")
  void shouldPublishFailureAndPropagate() {
    var sessionId = new SessionId(UUID.randomUUID());
    var command = new FinishSessionCommand(sessionId);
    doThrow(new RuntimeException("Session not found")).when(repository).getById(sessionId);

    assertThrows(RuntimeException.class, () -> handler.execute(command));
    verify(eventBus, times(1)).publish(any());
  }
}
