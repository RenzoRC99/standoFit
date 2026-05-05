package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.standofit.back.api.planning.dto.WorkoutDayDTO;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WorkoutDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class WorkoutDTO {

  private UUID id;

  private String name;

  private String description;

  private Boolean archived;

  @Valid
  private List<@Valid WorkoutDayDTO> days = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  public WorkoutDTO id(UUID id) {
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

  public WorkoutDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WorkoutDTO description(String description) {
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

  public WorkoutDTO archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

  /**
   * Get archived
   * @return archived
   */
  
  @Schema(name = "archived", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("archived")
  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public WorkoutDTO days(List<@Valid WorkoutDayDTO> days) {
    this.days = days;
    return this;
  }

  public WorkoutDTO addDaysItem(WorkoutDayDTO daysItem) {
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
  @Valid 
  @Schema(name = "days", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("days")
  public List<@Valid WorkoutDayDTO> getDays() {
    return days;
  }

  public void setDays(List<@Valid WorkoutDayDTO> days) {
    this.days = days;
  }

  public WorkoutDTO createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public WorkoutDTO updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkoutDTO workoutDTO = (WorkoutDTO) o;
    return Objects.equals(this.id, workoutDTO.id) &&
        Objects.equals(this.name, workoutDTO.name) &&
        Objects.equals(this.description, workoutDTO.description) &&
        Objects.equals(this.archived, workoutDTO.archived) &&
        Objects.equals(this.days, workoutDTO.days) &&
        Objects.equals(this.createdAt, workoutDTO.createdAt) &&
        Objects.equals(this.updatedAt, workoutDTO.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, archived, days, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkoutDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    days: ").append(toIndentedString(days)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

