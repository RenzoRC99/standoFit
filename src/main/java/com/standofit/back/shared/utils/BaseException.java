package com.standofit.back.shared.utils;

public abstract class BaseException extends RuntimeException{
    private final String context;

    protected BaseException(Class<?> clazz, String message) {
        super(message);
        this.context = clazz != null ? clazz.getSimpleName() : "Unknown";
    }

    @Override
    public String getMessage() {
        return String.format("%s -> %s : %s",
                this.getClass().getSimpleName(),
                context,
                super.getMessage());
    }


}