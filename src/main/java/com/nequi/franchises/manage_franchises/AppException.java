package com.nequi.franchises.manage_franchises;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {

    private final String errorCode;

    public AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
