package com.standofit.back.shared.api.dto;

import java.time.Instant;

public record ErrorDTO(String code, String message, Instant timestamp) {
  public ErrorDTO(String code, String message) {
    this(code, message, Instant.now());
  }
}
