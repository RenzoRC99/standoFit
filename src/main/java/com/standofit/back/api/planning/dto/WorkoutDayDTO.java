package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.standofit.back.api.planning.dto.WorkoutExerciseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WorkoutDayDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class WorkoutDayDTO {

  private UUID id;

  private String name;

  private Integer order;

  @Valid
  private List<@Valid WorkoutExerciseDTO> exercises = new ArrayList<>();

  public WorkoutDayDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public WorkoutDayDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WorkoutDayDTO order(Integer order) {
    this.order = order;
    return this;
  }

  /**
   * Get order
   * @return order
   */
  
  @Schema(name = "order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("order")
  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public WorkoutDayDTO exercises(List<@Valid WorkoutExerciseDTO> exercises) {
    this.exercises = exercises;
    return this;
  }

  public WorkoutDayDTO addExercisesItem(WorkoutExerciseDTO exercisesItem) {
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
  @Valid 
  @Schema(name = "exercises", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exercises")
  public List<@Valid WorkoutExerciseDTO> getExercises() {
    return exercises;
  }

  public void setExercises(List<@Valid WorkoutExerciseDTO> exercises) {
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
    WorkoutDayDTO workoutDayDTO = (WorkoutDayDTO) o;
    return Objects.equals(this.id, workoutDayDTO.id) &&
        Objects.equals(this.name, workoutDayDTO.name) &&
        Objects.equals(this.order, workoutDayDTO.order) &&
        Objects.equals(this.exercises, workoutDayDTO.exercises);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, order, exercises);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkoutDayDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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

