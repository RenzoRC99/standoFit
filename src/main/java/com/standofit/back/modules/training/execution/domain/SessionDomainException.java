package com.standofit.back.modules.training.execution.domain;

public class SessionDomainException extends RuntimeException {

  private final String code;

  public SessionDomainException(String code) {
    super(code);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
