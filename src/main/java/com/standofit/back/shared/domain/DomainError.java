package com.standofit.back.shared.domain;

public enum DomainError {
    NULL_VALUE("Value can not be null");


    private final String mesasge;


    DomainError(String mesasge) {
        this.mesasge = mesasge;
    }

    public String getMesasge(String className){
        return className + " : " + mesasge;
    }
}
