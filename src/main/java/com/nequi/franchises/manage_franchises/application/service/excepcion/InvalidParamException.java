package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.INVALID_PARAM;

public class InvalidParamException extends AppException {
    public InvalidParamException(Throwable cause) {
        super(INVALID_PARAM, cause);
    }
}
