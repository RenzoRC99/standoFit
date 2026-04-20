package com.standofit.back.shared.domain.valueobjects;

import com.standofit.back.shared.domain.valueobjects.errors.ValueObjectException;
import com.standofit.back.shared.domain.valueobjects.errors.ValueobjectErrors;

public abstract class BaseVO<T> {

    private final T value;

    protected BaseVO(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void validateNotNull() {
        if (value == null) {
            throw new ValueObjectException(this.getClass(), ValueobjectErrors.NULL_VALUE.getMessage());
        }
    }
}