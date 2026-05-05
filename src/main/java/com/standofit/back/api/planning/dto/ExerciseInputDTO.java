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
 * ExerciseInputDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class ExerciseInputDTO {

  private UUID exerciseId;

  private Integer sets;

  private Integer reps;

  private Integer restSeconds;

  public ExerciseInputDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ExerciseInputDTO(UUID exerciseId, Integer sets, Integer reps, Integer restSeconds) {
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.restSeconds = restSeconds;
  }

  public ExerciseInputDTO exerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
    return this;
  }

  /**
   * Get exerciseId
   * @return exerciseId
   */
  @NotNull @Valid 
  @Schema(name = "exerciseId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("exerciseId")
  public UUID getExerciseId() {
    return exerciseId;
  }

  public void setExerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
  }

  public ExerciseInputDTO sets(Integer sets) {
    this.sets = sets;
    return this;
  }

  /**
   * Get sets
   * @return sets
   */
  @NotNull 
  @Schema(name = "sets", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sets")
  public Integer getSets() {
    return sets;
  }

  public void setSets(Integer sets) {
    this.sets = sets;
  }

  public ExerciseInputDTO reps(Integer reps) {
    this.reps = reps;
    return this;
  }

  /**
   * Get reps
   * @return reps
   */
  @NotNull 
  @Schema(name = "reps", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reps")
  public Integer getReps() {
    return reps;
  }

  public void setReps(Integer reps) {
    this.reps = reps;
  }

  public ExerciseInputDTO restSeconds(Integer restSeconds) {
    this.restSeconds = restSeconds;
    return this;
  }

  /**
   * Get restSeconds
   * @return restSeconds
   */
  @NotNull 
  @Schema(name = "restSeconds", requiredMode = Schema.RequiredMode.REQUIRED)
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
    ExerciseInputDTO exerciseInputDTO = (ExerciseInputDTO) o;
    return Objects.equals(this.exerciseId, exerciseInputDTO.exerciseId) &&
        Objects.equals(this.sets, exerciseInputDTO.sets) &&
        Objects.equals(this.reps, exerciseInputDTO.reps) &&
        Objects.equals(this.restSeconds, exerciseInputDTO.restSeconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exerciseId, sets, reps, restSeconds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseInputDTO {\n");
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

