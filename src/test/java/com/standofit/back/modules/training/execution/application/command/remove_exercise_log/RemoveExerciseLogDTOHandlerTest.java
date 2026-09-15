package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
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
@DisplayName("Remove Exercise Log Handler Tests")
class RemoveExerciseLogDTOHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private ApplicationEventBus eventBus;

  private RemoveExerciseLogHandler handler;

  @BeforeEach
  void setUp() {
    handler = new RemoveExerciseLogHandler(repository, eventBus);
  }

  @Test
  @DisplayName("should remove exercise log, save and update read view on success")
  void shouldRemoveLogAndSave() {
    var sessionId = new SessionId(UUID.randomUUID());
    var logId = new ExerciseLogId(UUID.randomUUID());
    var command = new RemoveExerciseLogCommand(sessionId, logId);
    var session = Session.create(sessionId, new SessionDayId(UUID.randomUUID()));
    var logToAdd =
        ExerciseLog.create(
            logId,
            new ExerciseId(UUID.randomUUID()),
            new ExerciseLogSets(3),
            new ExerciseLogReps(10),
            new ExerciseLogWeight(50));
    session = session.addLog(logToAdd);
    when(repository.getById(sessionId)).thenReturn(session);
    when(repository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));

    handler.execute(command);

    verify(repository, times(1)).getById(sessionId);
    verify(repository, times(1)).save(any(Session.class));
    verify(eventBus, times(1)).publish(any());
  }

  @Test
  @DisplayName("should publish failure event and propagate exception")
  void shouldPublishFailureAndPropagate() {
    var sessionId = new SessionId(UUID.randomUUID());
    var logId = new ExerciseLogId(UUID.randomUUID());
    var command = new RemoveExerciseLogCommand(sessionId, logId);
    doThrow(new RuntimeException("Session not found")).when(repository).getById(sessionId);

    assertThrows(RuntimeException.class, () -> handler.execute(command));
    verify(eventBus, times(1)).publish(any());
  }
}
