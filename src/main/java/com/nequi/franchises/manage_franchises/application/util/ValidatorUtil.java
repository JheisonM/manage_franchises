package com.nequi.franchises.manage_franchises.application.util;

import com.nequi.franchises.manage_franchises.application.service.excepcion.InvalidParamException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class ValidatorUtil {

    private static Validator validator;

    public ValidatorUtil(Validator validator) {
        ValidatorUtil.validator = validator;
    }

    public static void validate(Object object) {
        try {
            if (object instanceof Collection<?>) {
                validateCollection((Collection<?>) object);
            } else {
                validateSingleObject(object);
            }
        } catch (RuntimeException e) {
            throw new InvalidParamException(e);
        }
    }

    private static void validateCollection(Collection<?> collection) {
        int index = 0;
        for (Object item : collection) {
            try {
                validateSingleObject(item);
            } catch (RuntimeException e) {
                String indexedMessage = String.format("At index [%d] %s", index, e.getMessage());
                throw new RuntimeException(indexedMessage);
            }
            index++;
        }
    }

    private static void validateSingleObject(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.isEmpty()) {
            return;
        }

        StringBuilder exceptionMessage = new StringBuilder("Validation failed for [")
                .append(object.getClass().getSimpleName())
                .append("]\n");

        violations.forEach(violation -> exceptionMessage.append(" -> Property '")
                .append(violation.getPropertyPath().toString())
                .append("' - Value [")
                .append(violation.getInvalidValue())
                .append("] - Cause [")
                .append(violation.getMessage())
                .append("]\n"));

        throw new RuntimeException(exceptionMessage.toString());
    }

}
