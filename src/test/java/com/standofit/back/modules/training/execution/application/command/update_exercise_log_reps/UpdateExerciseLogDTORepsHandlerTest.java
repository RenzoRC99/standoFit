package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Update Exercise Log Reps Handler Tests")
class UpdateExerciseLogDTORepsHandlerTest {

  @Mock private UpdateExerciseLogRepsService service;

  private UpdateExerciseLogRepsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateExerciseLogRepsHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new UpdateExerciseLogRepsCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogReps(12));

    handler.handle(command);

    verify(service, times(1)).updateReps(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new UpdateExerciseLogRepsCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogReps(12));
    doThrow(new RuntimeException("Service error")).when(service).updateReps(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
