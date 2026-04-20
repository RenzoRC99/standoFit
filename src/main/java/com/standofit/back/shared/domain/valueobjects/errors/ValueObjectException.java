package com.standofit.back.shared.domain.valueobjects.errors;

import com.standofit.back.shared.domain.DomainException;

public class ValueObjectException extends DomainException {
    public ValueObjectException(Class<?> clazz, String message) {
        super(clazz, message);
    }
}
