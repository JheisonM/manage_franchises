package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.*;

public class ProductException {
    public static class NotFound extends AppException {
        public NotFound() {
            super(PRODUCT_NOT_FOUND);
        }
    }

    public static class InvalidStock extends AppException {
        public InvalidStock() {
            super(INVALID_PRODUCT_STOCK);
        }
    }

    public static class InvalidName extends AppException {
        public InvalidName() {
            super(INVALID_PRODUCT_NAME);
        }
    }
}
