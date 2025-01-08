package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;
import com.nequi.franchises.manage_franchises.ErrorDefinition;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.*;

public abstract class ProductException extends AppException {

    public ProductException(ErrorDefinition errorDefinition) {
        super(errorDefinition);
    }

    public static class NotFound extends ProductException {
        public NotFound() {
            super(PRODUCT_NOT_FOUND);
        }
    }

    public static class InvalidStock extends ProductException {
        public InvalidStock() {
            super(INVALID_PRODUCT_STOCK);
        }
    }

    public static class InvalidName extends ProductException {
        public InvalidName() {
            super(INVALID_PRODUCT_NAME);
        }
    }
}
