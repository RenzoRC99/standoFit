package com.standofit.back.modules.training.execution.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.catalog.ExerciseVerifier;
import com.standofit.back.modules.training.execution.domain.catalog.WorkoutDayVerifier;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Session Domain Validator Tests")
class SessionDomainValidatorTest {

  @Mock private WorkoutDayVerifier workoutDayVerifier;
  @Mock private ExerciseVerifier exerciseVerifier;

  private SessionDomainValidator validator;

  @BeforeEach
  void setUp() {
    validator = new SessionDomainValidator(workoutDayVerifier, exerciseVerifier);
  }

  @Test
  @DisplayName("should not throw when day exists")
  void shouldNotThrowWhenDayExists() {
    var dayId = new SessionDayId(UUID.randomUUID());
    when(workoutDayVerifier.exists(dayId)).thenReturn(true);

    assertDoesNotThrow(() -> validator.ensureDayExists(dayId));
  }

  @Test
  @DisplayName("should throw when day does not exist")
  void shouldThrowWhenDayNotExists() {
    var dayId = new SessionDayId(UUID.randomUUID());
    when(workoutDayVerifier.exists(dayId)).thenReturn(false);

    var exception =
        assertThrows(SessionDomainException.class, () -> validator.ensureDayExists(dayId));
    assertTrue(exception.getMessage().contains("Workout day not found in planning"));
  }

  @Test
  @DisplayName("should not throw when exercise exists")
  void shouldNotThrowWhenExerciseExists() {
    var exerciseId = new ExerciseId(UUID.randomUUID());
    when(exerciseVerifier.exists(exerciseId)).thenReturn(true);

    assertDoesNotThrow(() -> validator.ensureExerciseExists(exerciseId));
  }

  @Test
  @DisplayName("should throw when exercise does not exist")
  void shouldThrowWhenExerciseNotExists() {
    var exerciseId = new ExerciseId(UUID.randomUUID());
    when(exerciseVerifier.exists(exerciseId)).thenReturn(false);

    var exception =
        assertThrows(
            SessionDomainException.class, () -> validator.ensureExerciseExists(exerciseId));
    assertTrue(exception.getMessage().contains("Exercise not found in catalog"));
  }
}
