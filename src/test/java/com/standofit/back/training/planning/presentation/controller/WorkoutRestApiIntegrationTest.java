package com.standofit.back.training.planning.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Workout REST API Integration Tests")
class WorkoutRestApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String createWorkout(String name) throws Exception {
        var request = new PlanWorkoutRequest(
                name,
                "Description",
                List.of(new PlanWorkoutRequest.DayRequest("Day 1", List.of())));

        return mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");
    }

    record PlanWorkoutRequest(String name, String description, List<DayRequest> days) {
        public record DayRequest(String name, List<ExerciseRequest> exercises) {
        }

        public record ExerciseRequest(UUID exerciseId, int sets, int reps, int restSeconds) {
        }
    }

    @Nested
    @DisplayName("POST /api/workouts - Plan Workout")
    class PlanWorkout {

        @Test
        @DisplayName("should create workout and return id")
        void shouldCreateWorkoutAndReturnId() throws Exception {
            var request = new PlanWorkoutRequest(
                    "Test Workout",
                    "Description",
                    List.of(new PlanWorkoutRequest.DayRequest("Monday", List.of())));

            mockMvc.perform(post("/api/workouts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$").isString());
        }
    }

    @Nested
    @DisplayName("GET /api/workouts/{id}")
    class GetWorkoutById {

        @Test
        @DisplayName("should return workout by id")
        void shouldReturnWorkoutById() throws Exception {
            String workoutId = createWorkout("Test Workout");

            mockMvc.perform(get("/api/workouts/" + workoutId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Test Workout"));
        }

        @Test
        @DisplayName("should return 404 when not found")
        void shouldReturn404WhenNotFound() throws Exception {
            mockMvc.perform(get("/api/workouts/" + UUID.randomUUID()))
                    .andExpect(status().isNotFound());
        }
    }
}
