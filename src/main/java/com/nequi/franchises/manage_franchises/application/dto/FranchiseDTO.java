package com.nequi.franchises.manage_franchises.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FranchiseDTO {
    private String id;
    @NotEmpty
    private String name;
}
