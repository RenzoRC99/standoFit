package com.standofit.back.shared.domain;

import com.standofit.back.shared.utils.BaseException;

public class InfrastructureException extends BaseException {

  protected InfrastructureException(Class<?> clazz, String message) {
    super(clazz, message);
  }
}
