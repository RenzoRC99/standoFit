package com.standofit.back.shared.domain;

public class DomainException extends RuntimeException{

    public DomainException(DomainError error, String className) {
        super(error.getMesasge(className));
    }
}
