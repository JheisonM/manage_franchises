package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;
import com.nequi.franchises.manage_franchises.ErrorDefinition;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.*;

public abstract class BranchException extends AppException{

    public BranchException(ErrorDefinition errorDefinition) {
        super(errorDefinition);
    }

    public static class NotFound extends BranchException {
        public NotFound() {
            super(BRANCH_NOT_FOUND);
        }
    }

    public static class NameAlreadyExists extends BranchException {
        public NameAlreadyExists() {
            super(BRANCH_NAME_ALREADY_EXISTS);
        }
    }

    public static class InvalidName extends BranchException {
        public InvalidName() {
            super(INVALID_PRODUCT_NAME);
        }
    }
}