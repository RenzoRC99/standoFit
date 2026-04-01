package com.standofit.back.shared.domain.valueobjects.errors;

import com.standofit.back.shared.utils.BaseException;

public class ValueObjectException extends BaseException {
    public ValueObjectException(Class<?> clazz, String message) {
        super(clazz, message);
    }
}
