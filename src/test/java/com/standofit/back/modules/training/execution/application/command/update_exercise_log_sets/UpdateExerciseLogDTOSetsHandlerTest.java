package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
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

  @Mock private UpdateExerciseLogSetsService service;

  private UpdateExerciseLogSetsHandler handler;

  @BeforeEach
  void setUp() {
    handler = new UpdateExerciseLogSetsHandler(service);
  }

  @Test
  @DisplayName("should delegate to service")
  void shouldDelegateToService() {
    var command =
        new UpdateExerciseLogSetsCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogSets(5));

    handler.handle(command);

    verify(service, times(1)).updateSets(command);
  }

  @Test
  @DisplayName("should propagate exception from service")
  void shouldPropagateExceptionFromService() {
    var command =
        new UpdateExerciseLogSetsCommand(
            new SessionId(UUID.randomUUID()),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseLogSets(5));
    doThrow(new RuntimeException("Service error")).when(service).updateSets(command);

    assertThrows(RuntimeException.class, () -> handler.handle(command));
  }
}
