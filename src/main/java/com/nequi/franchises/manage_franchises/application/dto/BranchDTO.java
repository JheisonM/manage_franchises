package com.nequi.franchises.manage_franchises.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    private String franchiseId;
    private boolean enabled;
}
