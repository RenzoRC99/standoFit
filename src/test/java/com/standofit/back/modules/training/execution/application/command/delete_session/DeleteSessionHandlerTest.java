package com.standofit.back.modules.training.execution.application.command.delete_session;

import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSessionHandlerTest {

  @Mock private SessionRepository repository;

  private DeleteSessionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new DeleteSessionHandler(repository);
  }

  @Test
  void should_call_repository_to_delete_session() {
    SessionId sessionId = new SessionId(UUID.randomUUID());
    DeleteSessionCommand command = new DeleteSessionCommand(sessionId);

    handler.handle(command);

    verify(repository, times(1)).delete(sessionId);
  }
}
