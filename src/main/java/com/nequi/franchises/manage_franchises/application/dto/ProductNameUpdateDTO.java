package com.nequi.franchises.manage_franchises.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNameUpdateDTO {
    @NotEmpty
    private String name;
}
