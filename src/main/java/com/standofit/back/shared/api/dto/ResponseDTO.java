package com.standofit.back.shared.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDTO<T>(
        T payload,
        ErrorDTO error
) {
    public static <T> ResponseDTO<T> ok(T payload) {
        return new ResponseDTO<>(payload, null);
    }

    public static <T> ResponseDTO<T> error(String code, String message) {
        return new ResponseDTO<>(null, new ErrorDTO(code, message));
    }

    public boolean isOk() {
        return error == null;
    }

    public boolean hasError() {
        return error != null;
    }
}