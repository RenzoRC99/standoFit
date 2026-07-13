package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

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
@DisplayName("Update Exercise Log Sets Handler Tests")
class UpdateExerciseLogDTOSetsHandlerTest {

  @Mock private SessionRepository repository;
  @Mock private ApplicationEventBus eventBus;

  private UpdateExerciseLogSetsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateExerciseLogSetsHandler(repository, eventBus);
  }

  @Test
  @DisplayName("should update exercise log sets and save session on success")
  void shouldUpdateSetsAndSave() {
    var sessionId = new SessionId(UUID.randomUUID());
    var logId = new ExerciseLogId(UUID.randomUUID());
    var command = new UpdateExerciseLogSetsCommand(sessionId, logId, new ExerciseLogSets(5));
    var session = Session.create(sessionId, new SessionDayId(UUID.randomUUID()));
    var log =
        ExerciseLog.create(
            logId,
            new ExerciseId(UUID.randomUUID()),
            new ExerciseLogSets(3),
            new ExerciseLogReps(10),
            new ExerciseLogWeight(50));
    session = session.addLog(log);
    when(repository.getById(sessionId)).thenReturn(session);
    when(repository.save(any(Session.class))).thenReturn(session);

    handler.handle(command);

    verify(repository, times(1)).getById(sessionId);
    verify(repository, times(1)).save(any(Session.class));
    verify(eventBus, times(1)).publish(any());
  }

  @Test
  @DisplayName("should publish failure event and propagate exception")
  void shouldPublishFailureAndPropagate() {
    var sessionId = new SessionId(UUID.randomUUID());
    var logId = new ExerciseLogId(UUID.randomUUID());
    var command = new UpdateExerciseLogSetsCommand(sessionId, logId, new ExerciseLogSets(5));
    doThrow(new RuntimeException("Session not found")).when(repository).getById(sessionId);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
    verify(eventBus, times(1)).publish(any());
  }
}
