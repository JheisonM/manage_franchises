package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.service.excepcion.BranchException;
import com.nequi.franchises.manage_franchises.application.service.excepcion.FranchiseException;
import com.nequi.franchises.manage_franchises.application.service.excepcion.InvalidParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.UNEXPECTED;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FranchiseException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleFranchiseNotFound(FranchiseException.NotFound e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(BranchException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleBranchNotFound(BranchException.NotFound e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParamException(InvalidParamException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(UNEXPECTED.getCode(), e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(String errorCode, String message) {
    }
}
