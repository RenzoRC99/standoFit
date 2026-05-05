package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.standofit.back.api.planning.dto.DayInputDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PlanWorkoutRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class PlanWorkoutRequest {

  private String name;

  private String description;

  @Valid
  private List<@Valid DayInputDTO> days = new ArrayList<>();

  public PlanWorkoutRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PlanWorkoutRequest(String name, List<@Valid DayInputDTO> days) {
    this.name = name;
    this.days = days;
  }

  public PlanWorkoutRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PlanWorkoutRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PlanWorkoutRequest days(List<@Valid DayInputDTO> days) {
    this.days = days;
    return this;
  }

  public PlanWorkoutRequest addDaysItem(DayInputDTO daysItem) {
    if (this.days == null) {
      this.days = new ArrayList<>();
    }
    this.days.add(daysItem);
    return this;
  }

  /**
   * Get days
   * @return days
   */
  @NotNull @Valid 
  @Schema(name = "days", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("days")
  public List<@Valid DayInputDTO> getDays() {
    return days;
  }

  public void setDays(List<@Valid DayInputDTO> days) {
    this.days = days;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlanWorkoutRequest planWorkoutRequest = (PlanWorkoutRequest) o;
    return Objects.equals(this.name, planWorkoutRequest.name) &&
        Objects.equals(this.description, planWorkoutRequest.description) &&
        Objects.equals(this.days, planWorkoutRequest.days);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, days);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlanWorkoutRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    days: ").append(toIndentedString(days)).append("\n");
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

