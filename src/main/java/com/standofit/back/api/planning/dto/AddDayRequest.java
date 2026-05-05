package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.standofit.back.api.planning.dto.ExerciseInputDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddDayRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class AddDayRequest {

  private String dayName;

  @Valid
  private List<@Valid ExerciseInputDTO> exercises = new ArrayList<>();

  public AddDayRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddDayRequest(String dayName, List<@Valid ExerciseInputDTO> exercises) {
    this.dayName = dayName;
    this.exercises = exercises;
  }

  public AddDayRequest dayName(String dayName) {
    this.dayName = dayName;
    return this;
  }

  /**
   * Get dayName
   * @return dayName
   */
  @NotNull 
  @Schema(name = "dayName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dayName")
  public String getDayName() {
    return dayName;
  }

  public void setDayName(String dayName) {
    this.dayName = dayName;
  }

  public AddDayRequest exercises(List<@Valid ExerciseInputDTO> exercises) {
    this.exercises = exercises;
    return this;
  }

  public AddDayRequest addExercisesItem(ExerciseInputDTO exercisesItem) {
    if (this.exercises == null) {
      this.exercises = new ArrayList<>();
    }
    this.exercises.add(exercisesItem);
    return this;
  }

  /**
   * Get exercises
   * @return exercises
   */
  @NotNull @Valid 
  @Schema(name = "exercises", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("exercises")
  public List<@Valid ExerciseInputDTO> getExercises() {
    return exercises;
  }

  public void setExercises(List<@Valid ExerciseInputDTO> exercises) {
    this.exercises = exercises;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddDayRequest addDayRequest = (AddDayRequest) o;
    return Objects.equals(this.dayName, addDayRequest.dayName) &&
        Objects.equals(this.exercises, addDayRequest.exercises);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayName, exercises);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddDayRequest {\n");
    sb.append("    dayName: ").append(toIndentedString(dayName)).append("\n");
    sb.append("    exercises: ").append(toIndentedString(exercises)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

