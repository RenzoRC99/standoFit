package com.standofit.back.shared.domain.valueobjects;

import com.standofit.back.shared.domain.valueobjects.errors.ValueObjectException;
import com.standofit.back.shared.domain.valueobjects.errors.ValueobjectErrors;

public abstract class StringVO extends BaseVO<String>{
    protected StringVO(String value) {
        super(value);
    }
    protected void ensureLengthRange(int min, int max){
        int length = value().length();
        if(length >max || length < min){
            String formattedMessage = String.format(ValueobjectErrors.INVALID_LENGTH_RANGE.getMessage(),min,max);
            throw new ValueObjectException(this.getClass(),formattedMessage);
        }
    }
    protected void ensureIsNotEmptyOrBlank(){
        if(value().isBlank() || value().isEmpty()){
            throw new ValueObjectException(this.getClass(),ValueobjectErrors.NOT_EMPTY.getMessage());
        }
    }
}
