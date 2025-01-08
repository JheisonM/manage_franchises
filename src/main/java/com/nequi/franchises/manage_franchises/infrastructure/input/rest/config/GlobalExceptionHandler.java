package com.nequi.franchises.manage_franchises.infrastructure.input.rest.config;

import com.nequi.franchises.manage_franchises.application.service.excepcion.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.UNEXPECTED;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FranchiseException.NotFound.class)
    public Mono<ResponseEntity<ErrorResponse>> handleFranchiseNotFound(FranchiseException.NotFound e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), NOT_FOUND));
    }

    @ExceptionHandler(BranchException.NotFound.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBranchNotFound(BranchException.NotFound e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), NOT_FOUND));
    }

    @ExceptionHandler(BranchException.NameAlreadyExists.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBranchNameAlreadyExists(BranchException.NameAlreadyExists e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), CONFLICT));
    }

    @ExceptionHandler(BranchException.InvalidName.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBranchInvalidName(BranchException.InvalidName e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), BAD_REQUEST));
    }

    @ExceptionHandler(ProductException.NotFound.class)
    public Mono<ResponseEntity<ErrorResponse>> handleProductNotFound(ProductException.NotFound e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), NOT_FOUND));
    }

    @ExceptionHandler(ProductException.InvalidStock.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidProductStock(ProductException.InvalidStock e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), BAD_REQUEST));
    }

    @ExceptionHandler(ProductException.InvalidName.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidProductName(ProductException.InvalidName e) {
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), BAD_REQUEST));
    }

    @ExceptionHandler(InvalidParamException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidParamException(InvalidParamException e) {
        log.error(e.getMessage(), e);
        return Mono.just(new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception e) {
        log.error("Unexpected error occurred: ", e);
        return Mono.just(new ResponseEntity<>(new ErrorResponse(UNEXPECTED.getCode(), e.getMessage()), INTERNAL_SERVER_ERROR));
    }

    public record ErrorResponse(String errorCode, String message) {
    }
}