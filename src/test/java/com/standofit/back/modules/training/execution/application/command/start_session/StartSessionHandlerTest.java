package com.standofit.back.modules.training.execution.application.command.start_session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
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

  @Mock private SessionRepository repository;
  @Mock private SessionReadViewUpdater readViewUpdater;
  @Mock private ApplicationEventBus eventBus;

  private StartSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new StartSessionHandler(repository, readViewUpdater, eventBus);
  }

  @Test
  @DisplayName("should create and save session on success")
  void shouldCreateAndSaveSession() {
    var command = new StartSessionCommand(new SessionDayId(UUID.randomUUID()));
    when(repository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));

    UUID result = handler.handle(command);

    assertNotNull(result);
    verify(repository, times(1)).save(any(Session.class));
    verify(readViewUpdater, times(1)).upsert(any(Session.class));
    verify(eventBus, times(1)).publish(any());
  }

  @Test
  @DisplayName("should publish failure event and propagate exception")
  void shouldPublishFailureAndPropagate() {
    var command = new StartSessionCommand(new SessionDayId(UUID.randomUUID()));
    doThrow(new RuntimeException("DB error")).when(repository).save(any(Session.class));

    assertThrows(RuntimeException.class, () -> handler.handle(command));
    verify(eventBus, times(1)).publish(any());
  }
}
