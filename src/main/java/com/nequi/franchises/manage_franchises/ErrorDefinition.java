package com.nequi.franchises.manage_franchises;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorDefinition {

    FRANCHISE_NOT_FOUND("0000", "Franchise not found"),
    BRANCH_NOT_FOUND("4567",""),
    INVALID_PARAM("0000", "Invalid parameter"),
    UNEXPECTED("000", ""),
    BRANCH_NAME_ALREADY_EXISTS("0002", "A branch with this name already exists"),
    PRODUCT_NOT_FOUND("0003", "Product not found"),
    INVALID_PRODUCT_STOCK("0004", "Invalid product stock quantity"),
    INVALID_PRODUCT_NAME("0005", "Invalid product name"),
    INVALID_BRANCH_NAME("0005", "Invalid branch name");

    private final String code;
    private final String message;
}
