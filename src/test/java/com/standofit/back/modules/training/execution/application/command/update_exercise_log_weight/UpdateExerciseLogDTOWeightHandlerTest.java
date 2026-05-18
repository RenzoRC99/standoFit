package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Update Exercise Log Weight Handler Tests")
class UpdateExerciseLogDTOWeightHandlerTest {

  @Mock private UpdateExerciseLogWeightService service;

  private UpdateExerciseLogWeightHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateExerciseLogWeightHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new UpdateExerciseLogWeightCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogWeight(60));

    handler.handle(command);

    verify(service, times(1)).updateWeight(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new UpdateExerciseLogWeightCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogWeight(60));
    doThrow(new RuntimeException("Service error")).when(service).updateWeight(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
