package com.standofit.back.modules.training.execution.infrastructure;

public class SessionInfrastructureException extends RuntimeException {

    private final String code;

    public SessionInfrastructureException(String code) {
        super(code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}