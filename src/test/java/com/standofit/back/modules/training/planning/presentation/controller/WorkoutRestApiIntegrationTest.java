package com.standofit.back.modules.training.planning.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Workout REST API Integration Tests")
class WorkoutRestApiIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private WorkoutJpaRepository workoutRepository;

  @BeforeEach
  void setUp() {
    workoutRepository.deleteAll();
  }

  private String extractIdFromResponse(String responseBody) throws Exception {
    var response =
        objectMapper.readValue(
            responseBody, com.standofit.back.api.planning.dto.WorkoutCreatedResponse.class);
    return response.getId().toString();
  }

  private String createWorkout(String name) throws Exception {
    var request =
        new com.standofit.back.api.planning.dto.PlanWorkoutRequest()
            .name(name)
            .description("Description")
            .days(
                List.of(
                    new com.standofit.back.api.planning.dto.DayInputDTO()
                        .name("Day 1")
                        .exercises(List.of())));

    return extractIdFromResponse(
        mockMvc
            .perform(
                post("/api/workouts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString());
  }

  private String createWorkoutWithDays(String name, List<String> dayNames) throws Exception {
    var request =
        new com.standofit.back.api.planning.dto.PlanWorkoutRequest()
            .name(name)
            .description("Description")
            .days(
                dayNames.stream()
                    .map(
                        d ->
                            new com.standofit.back.api.planning.dto.DayInputDTO()
                                .name(d)
                                .exercises(List.of()))
                    .toList());

    return extractIdFromResponse(
        mockMvc
            .perform(
                post("/api/workouts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString());
  }

  @Nested
  @DisplayName("POST /api/workouts - Plan Workout")
  class PlanWorkout {

    @Test
    @DisplayName("should create workout and return id")
    void shouldCreateWorkoutAndReturnId() throws Exception {
      createWorkout("Test Workout");
    }
  }

  @Nested
  @DisplayName("GET /api/workouts/{id}")
  class GetWorkoutById {

    @Test
    @DisplayName("should return workout by id")
    void shouldReturnWorkoutById() throws Exception {
      String workoutId = createWorkout("Test Workout");

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value("Test Workout"));
    }

    @Test
    @DisplayName("should return 404 when not found")
    void shouldReturn404WhenNotFound() throws Exception {
      mockMvc.perform(get("/api/workouts/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("PUT /api/workouts/{id}/name - Rename Workout")
  class RenameWorkout {

    @Test
    @DisplayName("should rename workout")
    void shouldRenameWorkout() throws Exception {
      String workoutId = createWorkout("Old Name");

      var renameRequest = new com.standofit.back.api.planning.dto.RenameRequest();
      renameRequest.setName("New Name");
      mockMvc
          .perform(
              put("/api/workouts/" + workoutId + "/name")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(renameRequest)))
          .andExpect(status().isNoContent());

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(jsonPath("$.name").value("New Name"));
    }
  }

  @Nested
  @DisplayName("DELETE /api/workouts/{id} - Delete Workout")
  class DeleteWorkout {

    @Test
    @DisplayName("should delete workout")
    void shouldDeleteWorkout() throws Exception {
      String workoutId = createWorkout("To Delete");

      mockMvc.perform(delete("/api/workouts/" + workoutId)).andExpect(status().isNoContent());

      mockMvc.perform(get("/api/workouts/" + workoutId)).andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("POST /api/workouts/{id}/days - Add Day")
  class AddDay {

    @Test
    @DisplayName("should add day to workout")
    void shouldAddDayToWorkout() throws Exception {
      String workoutId = createWorkout("Test Workout");

      var addDayRequest = new com.standofit.back.api.planning.dto.AddDayRequest();
      addDayRequest.setDayName("New Day");
      addDayRequest.setExercises(List.of());
      mockMvc
          .perform(
              post("/api/workouts/" + workoutId + "/days")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addDayRequest)))
          .andExpect(status().isCreated());

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(jsonPath("$.days.length()").value(2));
    }
  }

  @Nested
  @DisplayName("DELETE /api/workouts/{id}/days/{dayId} - Remove Day")
  class RemoveDay {

    @Test
    @DisplayName("should remove day from workout")
    void shouldRemoveDayFromWorkout() throws Exception {
      String workoutId = createWorkoutWithDays("Test", List.of("Day 1", "Day 2"));

      var workout =
          mockMvc.perform(get("/api/workouts/" + workoutId)).andExpect(status().isOk()).andReturn();
      String dayId =
          objectMapper
              .readTree(workout.getResponse().getContentAsString())
              .get("days")
              .get(0)
              .get("id")
              .asText();

      mockMvc
          .perform(delete("/api/workouts/" + workoutId + "/days/" + dayId))
          .andExpect(status().isNoContent());

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(jsonPath("$.days.length()").value(1));
    }
  }

  @Nested
  @DisplayName("POST /api/workouts/{id}/duplicate - Duplicate Workout")
  class DuplicateWorkout {

    @Test
    @DisplayName("should duplicate workout with new name")
    void shouldDuplicateWorkout() throws Exception {
      String workoutId = createWorkout("Original");

      var duplicateRequest = new com.standofit.back.api.planning.dto.DuplicateWorkoutRequest();
      duplicateRequest.setNewName("Copy");
      String newId =
          extractIdFromResponse(
              mockMvc
                  .perform(
                      post("/api/workouts/" + workoutId + "/duplicate")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(objectMapper.writeValueAsString(duplicateRequest)))
                  .andExpect(status().isCreated())
                  .andReturn()
                  .getResponse()
                  .getContentAsString());

      mockMvc.perform(get("/api/workouts/" + newId)).andExpect(jsonPath("$.name").value("Copy"));
    }

    @Test
    @DisplayName("should duplicate workout without new name and use default")
    void shouldDuplicateWithDefaultName() throws Exception {
      String workoutId = createWorkout("Original");

      String newId =
          extractIdFromResponse(
              mockMvc
                  .perform(
                      post("/api/workouts/" + workoutId + "/duplicate")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content("{}"))
                  .andExpect(status().isCreated())
                  .andReturn()
                  .getResponse()
                  .getContentAsString());

      mockMvc
          .perform(get("/api/workouts/" + newId))
          .andExpect(jsonPath("$.name").value("Original (Copy)"));
    }

    @Test
    @DisplayName("should duplicate workout with days and exercises")
    void shouldDuplicateWithDaysAndExercises() throws Exception {
      String workoutId = createWorkoutWithDays("Original", List.of("Day 1"));

      var duplicateRequest = new com.standofit.back.api.planning.dto.DuplicateWorkoutRequest();
      duplicateRequest.setNewName("Copy");
      String newId =
          extractIdFromResponse(
              mockMvc
                  .perform(
                      post("/api/workouts/" + workoutId + "/duplicate")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(objectMapper.writeValueAsString(duplicateRequest)))
                  .andExpect(status().isCreated())
                  .andReturn()
                  .getResponse()
                  .getContentAsString());

      mockMvc
          .perform(get("/api/workouts/" + newId))
          .andExpect(jsonPath("$.name").value("Copy"))
          .andExpect(jsonPath("$.days.length()").value(1));
    }
  }

  @Nested
  @DisplayName("PUT /api/workouts/{id}/description - Change Description")
  class ChangeDescription {

    @Test
    @DisplayName("should change workout description")
    void shouldChangeDescription() throws Exception {
      String workoutId = createWorkout("Test");

      var descRequest = new com.standofit.back.api.planning.dto.DescriptionRequest();
      descRequest.setDescription("New Description");
      mockMvc
          .perform(
              put("/api/workouts/" + workoutId + "/description")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(descRequest)))
          .andExpect(status().isNoContent());

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(jsonPath("$.description").value("New Description"));
    }
  }

  @Nested
  @DisplayName("PUT /api/workouts/{id}/days/reorder - Reorder Days")
  class ReorderDays {

    @Test
    @DisplayName("should reorder days")
    void shouldReorderDays() throws Exception {
      String workoutId = createWorkoutWithDays("Test", List.of("Day 1", "Day 2"));

      var workout =
          mockMvc.perform(get("/api/workouts/" + workoutId)).andExpect(status().isOk()).andReturn();
      var body = objectMapper.readTree(workout.getResponse().getContentAsString());
      String day1Id = body.get("days").get(0).get("id").asText();
      String day2Id = body.get("days").get(1).get("id").asText();

      var reorderRequest = new com.standofit.back.api.planning.dto.ReorderDaysRequest();
      reorderRequest.setDayIds(List.of(UUID.fromString(day2Id), UUID.fromString(day1Id)));
      mockMvc
          .perform(
              put("/api/workouts/" + workoutId + "/days/reorder")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(reorderRequest)))
          .andExpect(status().isNoContent());

      mockMvc
          .perform(get("/api/workouts/" + workoutId))
          .andExpect(jsonPath("$.days[0].name").value("Day 2"))
          .andExpect(jsonPath("$.days[1].name").value("Day 1"));
    }
  }
}
