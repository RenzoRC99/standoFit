package com.standofit.back.shared.domain.valueobjects;

import com.standofit.back.shared.domain.valueobjects.errors.ValueObjectException;
import com.standofit.back.shared.domain.valueobjects.errors.ValueobjectErrors;

public abstract class IntegerVO extends BaseVO<Integer> {
    protected IntegerVO(Integer value) {
        super(value);
    }
    protected void isBiggerThan(Integer max){
        if(value() > max){
            String formattedMessage = String.format(ValueobjectErrors.INT_BIGGER_THAN.getMessage(),max);
            throw new ValueObjectException(this.getClass(),formattedMessage);
        }
    }
    protected void isAtLeast(Integer min){
        if(value() < min){
            String formattedMessage = String.format(ValueobjectErrors.INT_SMALLER_THAN.getMessage(),min);
            throw new ValueObjectException(this.getClass(),formattedMessage);
        }
    }
}
