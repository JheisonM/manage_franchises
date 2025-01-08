package com.nequi.franchises.manage_franchises;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum ErrorDefinition {

    // Franchise Errors
    FRANCHISE_NOT_FOUND("1000", "Franchise not found."),

    // Branch Errors
    BRANCH_NOT_FOUND("2000", "Branch not found."),
    BRANCH_NAME_ALREADY_EXISTS("2001", "A branch with this name already exists."),
    INVALID_BRANCH_NAME("2002", "Invalid branch name."),

    // Product Errors
    PRODUCT_NOT_FOUND("3000", "Product not found."),
    INVALID_PRODUCT_STOCK("3001", "Invalid product stock quantity."),
    INVALID_PRODUCT_NAME("3002", "Invalid product name."),

    // Parameter Validation Errors
    INVALID_PARAM("4000", "Invalid parameter."),

    // General Errors
    UNEXPECTED("5000", "Unexpected error occurred.");

    private final String code;
    private final String message;
}
