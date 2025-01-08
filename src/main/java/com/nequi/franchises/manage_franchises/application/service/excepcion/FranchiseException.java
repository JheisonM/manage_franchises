package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;
import com.nequi.franchises.manage_franchises.ErrorDefinition;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.FRANCHISE_NOT_FOUND;

public abstract class FranchiseException extends AppException {

    public FranchiseException(ErrorDefinition errorDefinition) {
        super(errorDefinition);
    }

    public static class NotFound extends FranchiseException {
        public NotFound() {
            super(FRANCHISE_NOT_FOUND);
        }
    }
}
