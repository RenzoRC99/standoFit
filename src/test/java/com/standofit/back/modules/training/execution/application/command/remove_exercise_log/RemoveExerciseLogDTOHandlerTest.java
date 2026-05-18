package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
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

  @Mock private RemoveExerciseLogService service;

  private RemoveExerciseLogHandler handler;

  @BeforeEach
  void setUp() {
    handler = new RemoveExerciseLogHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new RemoveExerciseLogCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()));

    handler.handle(command);

    verify(service, times(1)).removeExerciseLog(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new RemoveExerciseLogCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()));
    doThrow(new RuntimeException("Service error")).when(service).removeExerciseLog(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
