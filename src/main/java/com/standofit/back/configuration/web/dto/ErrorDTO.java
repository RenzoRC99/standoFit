package com.standofit.back.configuration.web.dto;

import java.time.Instant;

public record ErrorDTO(String code, String message, Instant timestamp) {
  public ErrorDTO(String code, String message) {
    this(code, message, Instant.now());
  }
}
