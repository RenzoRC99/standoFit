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
 * StartSessionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class StartSessionRequest {

  private UUID dayId;

  public StartSessionRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StartSessionRequest(UUID dayId) {
    this.dayId = dayId;
  }

  public StartSessionRequest dayId(UUID dayId) {
    this.dayId = dayId;
    return this;
  }

  /**
   * Get dayId
   * @return dayId
   */
  @NotNull @Valid 
  @Schema(name = "dayId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dayId")
  public UUID getDayId() {
    return dayId;
  }

  public void setDayId(UUID dayId) {
    this.dayId = dayId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StartSessionRequest startSessionRequest = (StartSessionRequest) o;
    return Objects.equals(this.dayId, startSessionRequest.dayId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartSessionRequest {\n");
    sb.append("    dayId: ").append(toIndentedString(dayId)).append("\n");
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

