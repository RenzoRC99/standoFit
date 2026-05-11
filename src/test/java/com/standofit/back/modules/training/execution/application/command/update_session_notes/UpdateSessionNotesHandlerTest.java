package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSessionNotesHandlerTest {

  @Mock private SessionRepository repository;

  private UpdateSessionNotesHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateSessionNotesHandler(repository);
  }

  @Test
  void should_update_notes() {
    SessionId sessionId = new SessionId(java.util.UUID.randomUUID());
    SessionDayId dayId = new SessionDayId(java.util.UUID.randomUUID());
    WorkoutSessionNotes notes = new WorkoutSessionNotes("New notes");
    UpdateSessionNotesCommand command = new UpdateSessionNotesCommand(sessionId, notes);

    Session session = Session.create(sessionId, dayId);
    when(repository.findById(sessionId)).thenReturn(session);
    when(repository.save(any(Session.class))).thenReturn(session);

    handler.handle(command);

    verify(repository, times(1)).findById(sessionId);
    verify(repository, times(1)).save(any(Session.class));
  }

  @Test
  void should_throw_when_session_not_found() {
    SessionId sessionId = new SessionId(java.util.UUID.randomUUID());
    WorkoutSessionNotes notes = new WorkoutSessionNotes("Notes");
    UpdateSessionNotesCommand command = new UpdateSessionNotesCommand(sessionId, notes);

    when(repository.findById(sessionId)).thenReturn(null);

    assertThrows(NullPointerException.class, () -> handler.handle(command));
  }
}
