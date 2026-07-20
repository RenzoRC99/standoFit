package com.standofit.back.modules.training.planning.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.standofit.back.api.planning.dto.*;
import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.ExerciseMuscleGroup;
import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import com.standofit.back.shared.AbstractIntegrationTest;
import java.util.List;
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
@DisplayName("Workout REST API Integration Tests")
class WorkoutRestApiIntegrationTest extends AbstractIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private WorkoutJpaRepository workoutRepository;
  @Autowired private ExerciseRepository exerciseRepository;

  private UUID chestExerciseId;
  private UUID legsExerciseId;
  private UUID backExerciseId;

  @BeforeEach
  void setUp() {
    workoutRepository.deleteAll();
    exerciseRepository.deleteAll();
    chestExerciseId = createAndSaveExercise("Bench Press", ExerciseMuscleGroup.CHEST);
    legsExerciseId = createAndSaveExercise("Squat", ExerciseMuscleGroup.LEGS);
    backExerciseId = createAndSaveExercise("Pull Up", ExerciseMuscleGroup.BACK);
  }

  private UUID createAndSaveExercise(String name, ExerciseMuscleGroup group) {
    var exercise = new Exercise();
    exercise.setName(name);
    exercise.setDescription("Test " + name);
    exercise.setMuscleGroup(group);
    return UUID.fromString(exerciseRepository.save(exercise).getId());
  }

  private String extractIdFromResponse(String responseBody) throws Exception {
    return objectMapper.readValue(responseBody, WorkoutCreatedResponse.class).getId().toString();
  }

  @Test
  @DisplayName(
      "full workout lifecycle: create, read, update, add/reorder/remove day, duplicate, delete")
  void fullWorkoutLifecycle() throws Exception {
    var request =
        new PlanWorkoutRequest()
            .name("Upper/Lower Split")
            .description("A complete split routine")
            .days(
                List.of(
                    new DayInputDTO()
                        .name("Upper Body")
                        .exercises(
                            List.of(
                                new ExerciseInputDTO()
                                    .exerciseId(chestExerciseId)
                                    .sets(4)
                                    .reps(10)
                                    .restSeconds(90),
                                new ExerciseInputDTO()
                                    .exerciseId(backExerciseId)
                                    .sets(3)
                                    .reps(12)
                                    .restSeconds(60))),
                    new DayInputDTO()
                        .name("Lower Body")
                        .exercises(
                            List.of(
                                new ExerciseInputDTO()
                                    .exerciseId(legsExerciseId)
                                    .sets(5)
                                    .reps(8)
                                    .restSeconds(120)))));

    var createResponse =
        mockMvc
            .perform(
                post("/api/workouts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String workoutId = extractIdFromResponse(createResponse);

    mockMvc
        .perform(get("/api/workouts/" + workoutId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Upper/Lower Split"))
        .andExpect(jsonPath("$.description").value("A complete split routine"))
        .andExpect(jsonPath("$.days.length()").value(2))
        .andExpect(jsonPath("$.days[0].name").value("Upper Body"))
        .andExpect(jsonPath("$.days[0].exercises.length()").value(2))
        .andExpect(jsonPath("$.days[0].exercises[0].exerciseName").value("Bench Press"))
        .andExpect(jsonPath("$.days[0].exercises[1].exerciseName").value("Pull Up"))
        .andExpect(jsonPath("$.days[1].name").value("Lower Body"))
        .andExpect(jsonPath("$.days[1].exercises.length()").value(1))
        .andExpect(jsonPath("$.days[1].exercises[0].exerciseName").value("Squat"));

    mockMvc
        .perform(
            patch("/api/workouts/" + workoutId + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new RenameRequest("Upper/Lower Split V2"))))
        .andExpect(status().isNoContent());

    mockMvc
        .perform(get("/api/workouts/" + workoutId))
        .andExpect(jsonPath("$.name").value("Upper/Lower Split V2"));

    mockMvc
        .perform(
            patch("/api/workouts/" + workoutId + "/description")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new DescriptionRequest("Updated description"))))
        .andExpect(status().isNoContent());

    mockMvc
        .perform(get("/api/workouts/" + workoutId))
        .andExpect(jsonPath("$.description").value("Updated description"));

    mockMvc
        .perform(
            post("/api/workouts/" + workoutId + "/days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new AddDayRequest()
                            .dayName("Arms")
                            .exercises(
                                List.of(
                                    new ExerciseInputDTO()
                                        .exerciseId(chestExerciseId)
                                        .sets(3)
                                        .reps(10)
                                        .restSeconds(60))))))
        .andExpect(status().isCreated());

    var workoutAfterAdd =
        mockMvc
            .perform(get("/api/workouts/" + workoutId))
            .andExpect(jsonPath("$.days.length()").value(3))
            .andReturn();
    var daysNode =
        objectMapper.readTree(workoutAfterAdd.getResponse().getContentAsString()).get("days");
    String day1Id = daysNode.get(0).get("id").asText();
    String day2Id = daysNode.get(1).get("id").asText();
    String day3Id = daysNode.get(2).get("id").asText();

    mockMvc
        .perform(
            patch("/api/workouts/" + workoutId + "/days/reorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new ReorderDaysRequest()
                            .dayIds(
                                List.of(
                                    UUID.fromString(day3Id),
                                    UUID.fromString(day2Id),
                                    UUID.fromString(day1Id))))))
        .andExpect(status().isNoContent());

    mockMvc
        .perform(get("/api/workouts/" + workoutId))
        .andExpect(jsonPath("$.days[0].name").value("Arms"))
        .andExpect(jsonPath("$.days[2].name").value("Upper Body"));

    mockMvc
        .perform(delete("/api/workouts/" + workoutId + "/days/" + day1Id))
        .andExpect(status().isNoContent());

    mockMvc
        .perform(get("/api/workouts/" + workoutId))
        .andExpect(jsonPath("$.days.length()").value(2));

    var duplicateResponse =
        mockMvc
            .perform(
                post("/api/workouts/" + workoutId + "/duplicate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            new DuplicateWorkoutRequest().newName("Upper/Lower Split V2 (Copy)"))))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String copyId = extractIdFromResponse(duplicateResponse);

    mockMvc
        .perform(get("/api/workouts/" + copyId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Upper/Lower Split V2 (Copy)"))
        .andExpect(jsonPath("$.days.length()").value(2))
        .andExpect(jsonPath("$.description").value("Updated description"));

    mockMvc.perform(delete("/api/workouts/" + workoutId)).andExpect(status().isNoContent());

    mockMvc.perform(get("/api/workouts/" + workoutId)).andExpect(status().isNotFound());

    mockMvc
        .perform(get("/api/workouts/" + copyId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Upper/Lower Split V2 (Copy)"));
  }

  @Test
  @DisplayName("should return 404 when workout not found")
  void shouldReturn404WhenNotFound() throws Exception {
    mockMvc.perform(get("/api/workouts/" + UUID.randomUUID())).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("should return 404 when operating on non-existent workout")
  void shouldReturn404OnNonExistentWorkout() throws Exception {
    var fakeId = UUID.randomUUID();
    mockMvc
        .perform(
            patch("/api/workouts/" + fakeId + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RenameRequest("New Name"))))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("should return 400 when creating workout with invalid exerciseId")
  void shouldReturn400WhenExerciseNotFound() throws Exception {
    var request =
        new PlanWorkoutRequest()
            .name("Invalid Routine")
            .description("Has fake exercise")
            .days(
                List.of(
                    new DayInputDTO()
                        .name("Day 1")
                        .exercises(
                            List.of(
                                new ExerciseInputDTO()
                                    .exerciseId(UUID.randomUUID())
                                    .sets(3)
                                    .reps(10)
                                    .restSeconds(60)))));

    mockMvc
        .perform(
            post("/api/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"));
  }
}
