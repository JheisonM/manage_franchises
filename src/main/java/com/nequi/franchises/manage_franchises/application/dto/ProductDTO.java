package com.nequi.franchises.manage_franchises.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Positive
    private BigDecimal pricePerUnit;
    private String branchId;
    @PositiveOrZero
    private int quantity;
}

