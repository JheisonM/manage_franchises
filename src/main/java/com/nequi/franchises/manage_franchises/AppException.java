package com.nequi.franchises.manage_franchises;

import com.nequi.franchises.manage_franchises.application.util.LogUtil;
import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {

    private final String errorCode;
    private Throwable cause = null;

    @Override
    public String getMessage() {
        return this.cause == null ? super.getMessage() : String.format("%s -> %s -> %s",
                super.getMessage(),
                cause.getMessage(),
                LogUtil.getExceptionAndSuperclass(cause));
    }

    public AppException(ErrorDefinition errorDefinition) {
        super(errorDefinition.getMessage());
        this.errorCode = errorDefinition.getCode();
    }

    public AppException(ErrorDefinition errorDefinition, Throwable cause) {
        super(errorDefinition.getMessage(), cause);
        this.errorCode = errorDefinition.getCode();
        this.cause = cause;
    }


}
