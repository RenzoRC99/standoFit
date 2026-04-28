package com.standofit.back.api.execution.dto;

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
 * AddExerciseLogRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class AddExerciseLogRequest {

  private UUID logId;

  private UUID exerciseId;

  private Integer sets;

  private Integer reps;

  private Integer weight;

  public AddExerciseLogRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddExerciseLogRequest(UUID exerciseId, Integer sets, Integer reps, Integer weight) {
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.weight = weight;
  }

  public AddExerciseLogRequest logId(UUID logId) {
    this.logId = logId;
    return this;
  }

  /**
   * Get logId
   * @return logId
   */
  @Valid 
  @Schema(name = "logId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("logId")
  public UUID getLogId() {
    return logId;
  }

  public void setLogId(UUID logId) {
    this.logId = logId;
  }

  public AddExerciseLogRequest exerciseId(UUID exerciseId) {
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

  public AddExerciseLogRequest sets(Integer sets) {
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

  public AddExerciseLogRequest reps(Integer reps) {
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

  public AddExerciseLogRequest weight(Integer weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Get weight
   * @return weight
   */
  @NotNull 
  @Schema(name = "weight", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("weight")
  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddExerciseLogRequest addExerciseLogRequest = (AddExerciseLogRequest) o;
    return Objects.equals(this.logId, addExerciseLogRequest.logId) &&
        Objects.equals(this.exerciseId, addExerciseLogRequest.exerciseId) &&
        Objects.equals(this.sets, addExerciseLogRequest.sets) &&
        Objects.equals(this.reps, addExerciseLogRequest.reps) &&
        Objects.equals(this.weight, addExerciseLogRequest.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logId, exerciseId, sets, reps, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddExerciseLogRequest {\n");
    sb.append("    logId: ").append(toIndentedString(logId)).append("\n");
    sb.append("    exerciseId: ").append(toIndentedString(exerciseId)).append("\n");
    sb.append("    sets: ").append(toIndentedString(sets)).append("\n");
    sb.append("    reps: ").append(toIndentedString(reps)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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

