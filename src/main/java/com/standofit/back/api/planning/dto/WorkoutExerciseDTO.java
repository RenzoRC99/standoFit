package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WorkoutExerciseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class WorkoutExerciseDTO {

  private UUID id;

  private UUID exerciseId;

  private Integer sets;

  private Integer reps;

  private Integer restSeconds;

  public WorkoutExerciseDTO id(UUID id) {
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

  public WorkoutExerciseDTO exerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
    return this;
  }

  /**
   * Get exerciseId
   * @return exerciseId
   */
  @Valid 
  @Schema(name = "exerciseId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exerciseId")
  public UUID getExerciseId() {
    return exerciseId;
  }

  public void setExerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
  }

  public WorkoutExerciseDTO sets(Integer sets) {
    this.sets = sets;
    return this;
  }

  /**
   * Get sets
   * @return sets
   */
  
  @Schema(name = "sets", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sets")
  public Integer getSets() {
    return sets;
  }

  public void setSets(Integer sets) {
    this.sets = sets;
  }

  public WorkoutExerciseDTO reps(Integer reps) {
    this.reps = reps;
    return this;
  }

  /**
   * Get reps
   * @return reps
   */
  
  @Schema(name = "reps", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reps")
  public Integer getReps() {
    return reps;
  }

  public void setReps(Integer reps) {
    this.reps = reps;
  }

  public WorkoutExerciseDTO restSeconds(Integer restSeconds) {
    this.restSeconds = restSeconds;
    return this;
  }

  /**
   * Get restSeconds
   * @return restSeconds
   */
  
  @Schema(name = "restSeconds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("restSeconds")
  public Integer getRestSeconds() {
    return restSeconds;
  }

  public void setRestSeconds(Integer restSeconds) {
    this.restSeconds = restSeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkoutExerciseDTO workoutExerciseDTO = (WorkoutExerciseDTO) o;
    return Objects.equals(this.id, workoutExerciseDTO.id) &&
        Objects.equals(this.exerciseId, workoutExerciseDTO.exerciseId) &&
        Objects.equals(this.sets, workoutExerciseDTO.sets) &&
        Objects.equals(this.reps, workoutExerciseDTO.reps) &&
        Objects.equals(this.restSeconds, workoutExerciseDTO.restSeconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, exerciseId, sets, reps, restSeconds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkoutExerciseDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    exerciseId: ").append(toIndentedString(exerciseId)).append("\n");
    sb.append("    sets: ").append(toIndentedString(sets)).append("\n");
    sb.append("    reps: ").append(toIndentedString(reps)).append("\n");
    sb.append("    restSeconds: ").append(toIndentedString(restSeconds)).append("\n");
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

