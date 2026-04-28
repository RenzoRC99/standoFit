package com.standofit.back.api.execution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;

/** UpdateSetsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]",
    comments = "Generator version: 7.10.0")
public class UpdateSetsRequest {

  private Integer sets;

  public UpdateSetsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateSetsRequest(Integer sets) {
    this.sets = sets;
  }

  public UpdateSetsRequest sets(Integer sets) {
    this.sets = sets;
    return this;
  }

  /**
   * Get sets
   *
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateSetsRequest updateSetsRequest = (UpdateSetsRequest) o;
    return Objects.equals(this.sets, updateSetsRequest.sets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateSetsRequest {\n");
    sb.append("    sets: ").append(toIndentedString(sets)).append("\n");
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
