package com.standofit.back.shared.domain.valueobjects;

import com.standofit.back.shared.domain.DomainError;
import com.standofit.back.shared.domain.DomainException;

public abstract class BaseVO <T>  {

    private final T value;

    protected BaseVO(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void validateNotNull(){
        if(value == null){
            throw new DomainException(
                    DomainError.NULL_VALUE,getClass().getSimpleName()
            );
        }
    }
}
