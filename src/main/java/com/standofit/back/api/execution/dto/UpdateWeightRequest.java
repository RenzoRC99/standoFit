package com.standofit.back.api.execution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;

/** UpdateWeightRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]",
    comments = "Generator version: 7.10.0")
public class UpdateWeightRequest {

  private Integer weight;

  public UpdateWeightRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateWeightRequest(Integer weight) {
    this.weight = weight;
  }

  public UpdateWeightRequest weight(Integer weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Get weight
   *
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
    UpdateWeightRequest updateWeightRequest = (UpdateWeightRequest) o;
    return Objects.equals(this.weight, updateWeightRequest.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateWeightRequest {\n");
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
