package com.standofit.back.modules.training.planning.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.standofit.back.modules.training.planning.domain.WorkoutDomainException;
import com.standofit.back.modules.training.planning.domain.catalog.ExerciseCatalog;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Workout Domain Validator Tests")
class WorkoutDomainValidatorTest {

  @Mock private ExerciseCatalog exerciseCatalog;

  private WorkoutDomainValidator validator;

  @BeforeEach
  void setUp() {
    validator = new WorkoutDomainValidator(exerciseCatalog);
  }

  @Test
  @DisplayName("should not throw when all exercises exist")
  void shouldNotThrowWhenAllExist() {
    var id1 = new ExerciseId(UUID.randomUUID());
    var id2 = new ExerciseId(UUID.randomUUID());
    var data =
        new ExerciseCatalog.ExerciseData(
            Map.of(id1.value(), "Bench Press", id2.value(), "Squat"),
            Map.of(id1.value(), "CHEST", id2.value(), "LEGS"));

    when(exerciseCatalog.findByIds(Set.of(id1.value(), id2.value()))).thenReturn(data);

    assertDoesNotThrow(() -> validator.ensureExercisesExist(List.of(id1, id2)));
  }

  @Test
  @DisplayName("should not throw when list is empty")
  void shouldNotThrowWhenEmpty() {
    assertDoesNotThrow(() -> validator.ensureExercisesExist(List.of()));
    verifyNoInteractions(exerciseCatalog);
  }

  @Test
  @DisplayName("should throw when some exercises do not exist")
  void shouldThrowWhenSomeMissing() {
    var existing = new ExerciseId(UUID.randomUUID());
    var missing = new ExerciseId(UUID.randomUUID());
    var data =
        new ExerciseCatalog.ExerciseData(
            Map.of(existing.value(), "Bench Press"), Map.of(existing.value(), "CHEST"));

    when(exerciseCatalog.findByIds(Set.of(existing.value(), missing.value()))).thenReturn(data);

    var exception =
        assertThrows(
            WorkoutDomainException.class,
            () -> validator.ensureExercisesExist(List.of(existing, missing)));
    assertTrue(exception.getMessage().contains("Exercise not found in catalog"));
  }
}
