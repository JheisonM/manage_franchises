package com.nequi.franchises.manage_franchises;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorDefinition {

    FRANCHISE_NOT_FOUND("0000", "Franchise not found"),
    INVALID_PARAM("0000", "Invalid parameter");

    private final String code;
    private final String message;
}
