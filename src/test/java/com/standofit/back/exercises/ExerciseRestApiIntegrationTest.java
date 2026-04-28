package com.standofit.back.exercises;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.standofit.back.modules.exercises.repository.ExerciseRepository;
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
@DisplayName("Exercise REST API Integration Tests")
class ExerciseRestApiIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ExerciseRepository exerciseRepository;

  @BeforeEach
  void setUp() {
    exerciseRepository.deleteAll();
  }

  @Nested
  @DisplayName("POST /api/exercises")
  class CreateExercise {

    @Test
    @DisplayName("should create exercise")
    void shouldCreateExercise() throws Exception {
      mockMvc
          .perform(
              post("/api/exercises")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      """
                            {
                                "name": "Bench Press",
                                "description": "Chest exercise",
                                "muscleGroup": "CHEST"
                            }
                            """))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.name").value("Bench Press"));
    }
  }

  @Nested
  @DisplayName("GET /api/exercises")
  class GetExercises {

    @Test
    @DisplayName("should get all exercises")
    void shouldGetAllExercises() throws Exception {
      mockMvc.perform(get("/api/exercises")).andExpect(status().isOk());
    }
  }

  @Nested
  @DisplayName("GET /api/exercises/{id}")
  class GetById {

    @Test
    @DisplayName("should return 404 when not found")
    void shouldReturn404WhenNotFound() throws Exception {
      mockMvc.perform(get("/api/exercises/123")).andExpect(status().isNotFound());
    }
  }
}
