package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Update Session Notes Handler Tests")
class UpdateSessionNotesHandlerTest {

  @Mock private UpdateSessionNotesService service;

  private UpdateSessionNotesHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateSessionNotesHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new UpdateSessionNotesCommand(
            new SessionId(UUID.randomUUID()),
            new WorkoutSessionNotes("Updated notes"));

    handler.handle(command);

    verify(service, times(1)).updateNotes(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new UpdateSessionNotesCommand(
            new SessionId(UUID.randomUUID()),
            new WorkoutSessionNotes("Updated notes"));
    doThrow(new RuntimeException("Service error")).when(service).updateNotes(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
