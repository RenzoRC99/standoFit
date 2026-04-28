package com.standofit.back.api.execution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;

/** UpdateRepsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]",
    comments = "Generator version: 7.10.0")
public class UpdateRepsRequest {

  private Integer reps;

  public UpdateRepsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateRepsRequest(Integer reps) {
    this.reps = reps;
  }

  public UpdateRepsRequest reps(Integer reps) {
    this.reps = reps;
    return this;
  }

  /**
   * Get reps
   *
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateRepsRequest updateRepsRequest = (UpdateRepsRequest) o;
    return Objects.equals(this.reps, updateRepsRequest.reps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateRepsRequest {\n");
    sb.append("    reps: ").append(toIndentedString(reps)).append("\n");
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
