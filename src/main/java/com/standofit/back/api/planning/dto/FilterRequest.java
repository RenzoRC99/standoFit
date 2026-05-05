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
 * FilterRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class FilterRequest {

  private String field;

  private String operator;

  private String value;

  public FilterRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FilterRequest(String field, String operator, String value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public FilterRequest field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   */
  @NotNull 
  @Schema(name = "field", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public FilterRequest operator(String operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Get operator
   * @return operator
   */
  @NotNull 
  @Schema(name = "operator", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operator")
  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public FilterRequest value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @NotNull 
  @Schema(name = "value", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilterRequest filterRequest = (FilterRequest) o;
    return Objects.equals(this.field, filterRequest.field) &&
        Objects.equals(this.operator, filterRequest.operator) &&
        Objects.equals(this.value, filterRequest.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, operator, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FilterRequest {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

