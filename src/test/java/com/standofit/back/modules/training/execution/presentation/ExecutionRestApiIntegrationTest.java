package com.standofit.back.modules.training.execution.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.standofit.back.api.execution.dto.*;
import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.ExerciseMuscleGroup;
import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutDayJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.entity.WorkoutJpaEntity;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import com.standofit.back.shared.AbstractIntegrationTest;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Session REST API Integration Tests")
class ExecutionRestApiIntegrationTest extends AbstractIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ExerciseRepository exerciseRepository;
  @Autowired private WorkoutJpaRepository workoutJpaRepository;

  private UUID exerciseId;
  private UUID dayId;

  @BeforeEach
  void setUp() {
    exerciseRepository.deleteAll();
    workoutJpaRepository.deleteAll();

    exerciseId = createAndSaveExercise("Bench Press", ExerciseMuscleGroup.CHEST);
    dayId = createAndSaveWorkoutWithDay("Test Workout", "Test Day");
  }

  private UUID createAndSaveExercise(String name, ExerciseMuscleGroup group) {
    var exercise = new Exercise();
    exercise.setName(name);
    exercise.setDescription("Test " + name);
    exercise.setMuscleGroup(group);
    return UUID.fromString(exerciseRepository.save(exercise).getId());
  }

  private UUID createAndSaveWorkoutWithDay(String workoutName, String dayName) {
    var workout =
        new WorkoutJpaEntity(
            UUID.randomUUID(), workoutName, "Test description", Instant.now(), Instant.now());
    var day = new WorkoutDayJpaEntity(UUID.randomUUID(), dayName, 0);
    workout.addDay(day);
    workoutJpaRepository.save(workout);
    return day.getId();
  }

  @Test
  @DisplayName("full session lifecycle: start, add log, finish, delete")
  void fullSessionLifecycle() throws Exception {
    var startRequest = new StartSessionRequest(dayId);

    var createResponse =
        mockMvc
            .perform(
                post("/api/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(startRequest)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String sessionId = createResponse.replace("\"", "");

    mockMvc
        .perform(get("/api/sessions/" + sessionId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(sessionId))
        .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

    var addLogRequest =
        new AddExerciseLogRequest().exerciseId(exerciseId).sets(4).reps(10).weight(60);

    mockMvc
        .perform(
            post("/api/sessions/" + sessionId + "/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLogRequest)))
        .andExpect(status().isCreated());

    mockMvc
        .perform(get("/api/sessions/" + sessionId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.logs[0].exerciseName").value("Bench Press"))
        .andExpect(jsonPath("$.logs[0].muscleGroup").value(ExerciseMuscleGroup.CHEST.name()));

    mockMvc
        .perform(
            post("/api/sessions/" + sessionId + "/finish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isNoContent());

    mockMvc
        .perform(get("/api/sessions/" + sessionId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("COMPLETED"));

    mockMvc.perform(delete("/api/sessions/" + sessionId)).andExpect(status().isNoContent());

    mockMvc.perform(get("/api/sessions/" + sessionId)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("should return 400 when starting session with invalid dayId")
  void shouldReturn400WhenDayNotFound() throws Exception {
    var startRequest = new StartSessionRequest(UUID.randomUUID());

    mockMvc
        .perform(
            post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(startRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"));
  }

  @Test
  @DisplayName("should return 400 when adding log with invalid exerciseId")
  void shouldReturn400WhenExerciseNotFound() throws Exception {
    var startRequest = new StartSessionRequest(dayId);

    var createResponse =
        mockMvc
            .perform(
                post("/api/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(startRequest)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String sessionId = createResponse.replace("\"", "");

    var addLogRequest =
        new AddExerciseLogRequest().exerciseId(UUID.randomUUID()).sets(4).reps(10).weight(60);

    mockMvc
        .perform(
            post("/api/sessions/" + sessionId + "/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLogRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"));
  }

  @Test
  @DisplayName("should return 404 when session not found")
  void shouldReturn404WhenNotFound() throws Exception {
    mockMvc.perform(get("/api/sessions/" + UUID.randomUUID())).andExpect(status().isNotFound());
  }
}
