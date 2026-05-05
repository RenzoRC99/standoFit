package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DuplicateWorkoutRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class DuplicateWorkoutRequest {

  private String newName;

  public DuplicateWorkoutRequest newName(String newName) {
    this.newName = newName;
    return this;
  }

  /**
   * Get newName
   * @return newName
   */
  
  @Schema(name = "newName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newName")
  public String getNewName() {
    return newName;
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DuplicateWorkoutRequest duplicateWorkoutRequest = (DuplicateWorkoutRequest) o;
    return Objects.equals(this.newName, duplicateWorkoutRequest.newName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DuplicateWorkoutRequest {\n");
    sb.append("    newName: ").append(toIndentedString(newName)).append("\n");
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

