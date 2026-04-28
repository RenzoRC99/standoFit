package com.standofit.back.api.execution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;

/** ResponseDTO */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-04-27T11:22:34.370993867+02:00[Europe/Madrid]",
    comments = "Generator version: 7.10.0")
public class ResponseDTO {

  private Object payload;

  private ErrorDTO error;

  public ResponseDTO payload(Object payload) {
    this.payload = payload;
    return this;
  }

  /**
   * Get payload
   *
   * @return payload
   */
  @Schema(name = "payload", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("payload")
  public Object getPayload() {
    return payload;
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }

  public ResponseDTO error(ErrorDTO error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   *
   * @return error
   */
  @Valid
  @Schema(name = "error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error")
  public ErrorDTO getError() {
    return error;
  }

  public void setError(ErrorDTO error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseDTO responseDTO = (ResponseDTO) o;
    return Objects.equals(this.payload, responseDTO.payload)
        && Objects.equals(this.error, responseDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payload, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseDTO {\n");
    sb.append("    payload: ").append(toIndentedString(payload)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
