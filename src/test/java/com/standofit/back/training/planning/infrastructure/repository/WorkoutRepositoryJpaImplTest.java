package com.standofit.back.training.planning.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutRepositoryJpaImpl;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("Workout Repository Tests")
@Transactional
@Rollback(false)
class WorkoutRepositoryJpaImplTest {

  @Autowired private WorkoutRepositoryJpaImpl repository;

  @Autowired private WorkoutJpaRepository jpaRepository;

  @BeforeEach
  void setUp() {
    jpaRepository.deleteAll();
  }

  private Workout createTestWorkout() {
    WorkoutDay day =
        WorkoutDay.create(
            new WorkoutDayId(UUID.randomUUID()), new WorkoutDayName("Day 1"), List.of());

    return Workout.create(
        new WorkoutId(UUID.randomUUID()), new WorkoutDescription(null), new WorkoutName("Test Workout"), List.of(day));
  }

  private Workout createTestWorkoutWithExercises() {
    WorkoutExercise exercise =
        WorkoutExercise.create(
            new com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId(
                UUID.randomUUID()),
            new ExerciseId(UUID.randomUUID()),
            new WorkoutExerciseSets(3),
            new WorkoutExerciseReps(10),
            new WorkoutExerciseRest(60));

    WorkoutDay day =
        WorkoutDay.create(
            new WorkoutDayId(UUID.randomUUID()), new WorkoutDayName("Day 1"), List.of(exercise));

    return Workout.create(
        new WorkoutId(UUID.randomUUID()), new WorkoutDescription(null), new WorkoutName("Test Workout"), List.of(day));
  }

  @Nested
  @DisplayName("Save")
  class Save {

    @Test
    @DisplayName("should save workout")
    void shouldSaveWorkout() {
      Workout workout = createTestWorkout();

      Workout saved = repository.save(workout);

      assertNotNull(saved);
      assertNotNull(saved.getId());
      assertEquals(workout.getName().value(), saved.getName().value());
    }

    @Test
    @DisplayName("should save workout with exercises")
    void shouldSaveWorkoutWithExercises() {
      Workout workout = createTestWorkoutWithExercises();

      Workout saved = repository.save(workout);

      assertNotNull(saved);
      assertFalse(saved.getDays().isEmpty());
      assertFalse(saved.getDays().getFirst().getExercises().isEmpty());
    }
  }

  @Nested
  @DisplayName("Find By Id")
  class FindById {

    @Test
    @DisplayName("should find workout by id")
    void shouldFindWorkoutById() {
      Workout workout = createTestWorkout();
      Workout saved = repository.save(workout);
      WorkoutId id = saved.getId();

      var result = repository.findById(id);

      assertTrue(result.isPresent());
      assertEquals(saved.getId(), result.get().getId());
      assertEquals(saved.getName().value(), result.get().getName().value());
    }

    @Test
    @DisplayName("should return empty when id not found")
    void shouldReturnEmptyWhenIdNotFound() {
      var result = repository.findById(new WorkoutId(UUID.randomUUID()));

      assertTrue(result.isEmpty());
    }
  }

  @Nested
  @DisplayName("Delete By Id")
  class DeleteById {

    @Test
    @DisplayName("should delete workout by id")
    void shouldDeleteWorkoutById() {
      Workout workout = createTestWorkout();
      Workout saved = repository.save(workout);
      WorkoutId id = saved.getId();

      repository.deleteById(id);

      assertFalse(jpaRepository.existsById(id.value()));
    }

    @Test
    @DisplayName("should not throw when id not found")
    void shouldNotThrowWhenIdNotFound() {
      assertDoesNotThrow(() -> repository.deleteById(new WorkoutId(UUID.randomUUID())));
    }
  }
}
