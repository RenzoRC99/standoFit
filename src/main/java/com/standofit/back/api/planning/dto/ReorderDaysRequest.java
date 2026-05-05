package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReorderDaysRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class ReorderDaysRequest {

  @Valid
  private List<UUID> dayIds = new ArrayList<>();

  public ReorderDaysRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ReorderDaysRequest(List<UUID> dayIds) {
    this.dayIds = dayIds;
  }

  public ReorderDaysRequest dayIds(List<UUID> dayIds) {
    this.dayIds = dayIds;
    return this;
  }

  public ReorderDaysRequest addDayIdsItem(UUID dayIdsItem) {
    if (this.dayIds == null) {
      this.dayIds = new ArrayList<>();
    }
    this.dayIds.add(dayIdsItem);
    return this;
  }

  /**
   * Get dayIds
   * @return dayIds
   */
  @NotNull @Valid 
  @Schema(name = "dayIds", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dayIds")
  public List<UUID> getDayIds() {
    return dayIds;
  }

  public void setDayIds(List<UUID> dayIds) {
    this.dayIds = dayIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReorderDaysRequest reorderDaysRequest = (ReorderDaysRequest) o;
    return Objects.equals(this.dayIds, reorderDaysRequest.dayIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReorderDaysRequest {\n");
    sb.append("    dayIds: ").append(toIndentedString(dayIds)).append("\n");
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

