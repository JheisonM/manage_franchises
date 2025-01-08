package com.nequi.franchises.manage_franchises.application.service.excepcion;

import com.nequi.franchises.manage_franchises.AppException;

import static com.nequi.franchises.manage_franchises.ErrorDefinition.BRANCH_NAME_ALREADY_EXISTS;
import static com.nequi.franchises.manage_franchises.ErrorDefinition.BRANCH_NOT_FOUND;

public class BranchException {
    public static class NotFound extends AppException {
        public NotFound() {
            super(BRANCH_NOT_FOUND);
        }
    }

    public static class NameAlreadyExists extends AppException {
        public NameAlreadyExists() {
            super(BRANCH_NAME_ALREADY_EXISTS);
        }
    }
}