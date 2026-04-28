package com.standofit.back.api.execution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/** ExerciseLog */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]",
    comments = "Generator version: 7.10.0")
public class ExerciseLogDTO {

  private UUID id;

  private UUID exerciseId;

  private Integer sets;

  private Integer reps;

  private Integer weight;

  public ExerciseLogDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
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

  public ExerciseLogDTO exerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
    return this;
  }

  /**
   * Get exerciseId
   *
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

  public ExerciseLogDTO sets(Integer sets) {
    this.sets = sets;
    return this;
  }

  /**
   * Get sets
   *
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

  public ExerciseLogDTO reps(Integer reps) {
    this.reps = reps;
    return this;
  }

  /**
   * Get reps
   *
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

  public ExerciseLogDTO weight(Integer weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Get weight
   *
   * @return weight
   */
  @Schema(name = "weight", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    ExerciseLogDTO exerciseLogDTO = (ExerciseLogDTO) o;
    return Objects.equals(this.id, exerciseLogDTO.id)
        && Objects.equals(this.exerciseId, exerciseLogDTO.exerciseId)
        && Objects.equals(this.sets, exerciseLogDTO.sets)
        && Objects.equals(this.reps, exerciseLogDTO.reps)
        && Objects.equals(this.weight, exerciseLogDTO.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, exerciseId, sets, reps, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseLog {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    exerciseId: ").append(toIndentedString(exerciseId)).append("\n");
    sb.append("    sets: ").append(toIndentedString(sets)).append("\n");
    sb.append("    reps: ").append(toIndentedString(reps)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
