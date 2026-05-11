package com.standofit.back.shared.domain;

import com.standofit.back.shared.utils.BaseException;

public class ApplicationException extends BaseException {
    
    public ApplicationException(Class<?> clazz, String message, Throwable cause) {
        super(clazz, cause != null ? message + " | cause: " + cause.getMessage() : message);
    }
}
