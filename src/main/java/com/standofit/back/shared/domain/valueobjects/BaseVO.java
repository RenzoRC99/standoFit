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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseVO<?> baseVO = (BaseVO<?>) o;
        return value != null ? value.equals(baseVO.value) : baseVO.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}