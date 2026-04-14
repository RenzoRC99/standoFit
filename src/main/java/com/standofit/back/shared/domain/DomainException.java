package com.standofit.back.shared.domain;

import com.standofit.back.shared.utils.BaseException;

public abstract class DomainException extends BaseException {

    protected DomainException(Class<?> clazz, String message) {
        super(clazz, message);
    }

    protected DomainException(Class<?> clazz, String message, Throwable cause) {
        super(clazz, message);
        initCause(cause);
    }
}
