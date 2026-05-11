package com.standofit.back.training.planning.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.standofit.back.modules.training.planning.infrastructure.repository.WorkoutJpaRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Workout Search Criteria Integration Tests")
class WorkoutSearchCriteriaIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private WorkoutJpaRepository workoutRepository;

  @BeforeEach
  void setUp() {
    workoutRepository.deleteAll();
  }

  @Test
  @DisplayName("should create workouts and search with criteria")
  void shouldCreateAndSearchWithCriteria() throws Exception {
    // Create 3 workouts
    createWorkout("Full Body Workout");
    createWorkout("Upper Body Workout");
    createWorkout("Full Leg Day");

    // Search without filters - should return all 3
    var searchAll = new SearchRequest(null, null, null, 0, 10);
    mockMvc
        .perform(
            post("/api/workouts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchAll)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.content.length()").value(3));

    // Search with LIKE filter "full" - should return 2
    var searchFull =
        new SearchRequest(
            List.of(new SearchRequest.FilterRequest("name", "LIKE", "full")), "name", "ASC", 0, 10);
    mockMvc
        .perform(
            post("/api/workouts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchFull)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(2))
        .andExpect(jsonPath("$.content[0].name").value("Full Body Workout"))
        .andExpect(jsonPath("$.content[1].name").value("Full Leg Day"));

    // Search with pagination - page 0, size 2
    var searchPage = new SearchRequest(null, "createdAt", "DESC", 0, 2);
    mockMvc
        .perform(
            post("/api/workouts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchPage)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.content.length()").value(2))
        .andExpect(jsonPath("$.pageNumber").value(0))
        .andExpect(jsonPath("$.pageSize").value(2));
  }

  @Test
  @DisplayName("should return empty when no matches")
  void shouldReturnEmptyWhenNoMatches() throws Exception {
    createWorkout("Test Workout");

    var search =
        new SearchRequest(
            List.of(new SearchRequest.FilterRequest("name", "LIKE", "nonexistent")),
            "name",
            "ASC",
            0,
            10);

    mockMvc
        .perform(
            post("/api/workouts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(search)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(0))
        .andExpect(jsonPath("$.content.length()").value(0));
  }

  private void createWorkout(String name) throws Exception {
    var request =
        new PlanWorkoutRequest(
            name, "Description", List.of(new PlanWorkoutRequest.DayRequest("Day 1", List.of())));

    mockMvc
        .perform(
            post("/api/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  record SearchRequest(
      List<FilterRequest> filters, String orderBy, String order, int page, int pageSize) {
    public record FilterRequest(String field, String operator, Object value) {}
  }

  record PlanWorkoutRequest(String name, String description, List<DayRequest> days) {
    public record DayRequest(String name, List<ExerciseRequest> exercises) {}

    public record ExerciseRequest(UUID exerciseId, int sets, int reps, int restSeconds) {}
  }
}
