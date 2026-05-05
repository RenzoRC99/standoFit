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
 * DayInputDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class DayInputDTO {

  private String name;

  @Valid
  private List<@Valid ExerciseInputDTO> exercises = new ArrayList<>();

  public DayInputDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DayInputDTO(String name, List<@Valid ExerciseInputDTO> exercises) {
    this.name = name;
    this.exercises = exercises;
  }

  public DayInputDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DayInputDTO exercises(List<@Valid ExerciseInputDTO> exercises) {
    this.exercises = exercises;
    return this;
  }

  public DayInputDTO addExercisesItem(ExerciseInputDTO exercisesItem) {
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
    DayInputDTO dayInputDTO = (DayInputDTO) o;
    return Objects.equals(this.name, dayInputDTO.name) &&
        Objects.equals(this.exercises, dayInputDTO.exercises);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, exercises);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DayInputDTO {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

