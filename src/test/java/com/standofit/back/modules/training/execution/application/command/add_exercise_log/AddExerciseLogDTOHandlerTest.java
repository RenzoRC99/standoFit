package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Add Exercise Log Handler Tests")
class AddExerciseLogDTOHandlerTest {

  @Mock private AddExerciseLogService service;

  private AddExerciseLogHandler handler;

  @BeforeEach
  void setUp() {
    handler = new AddExerciseLogHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new AddExerciseLogCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseId(UUID.randomUUID()),
            new ExerciseLogSets(3),
            new ExerciseLogReps(10),
            new ExerciseLogWeight(50));

    handler.handle(command);

    verify(service, times(1)).addExerciseLog(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new AddExerciseLogCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseId(UUID.randomUUID()),
            new ExerciseLogSets(3),
            new ExerciseLogReps(10),
            new ExerciseLogWeight(50));
    doThrow(new RuntimeException("Service error")).when(service).addExerciseLog(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
