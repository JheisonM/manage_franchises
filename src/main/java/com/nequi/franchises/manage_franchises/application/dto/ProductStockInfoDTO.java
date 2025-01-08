package com.nequi.franchises.manage_franchises.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockInfoDTO {
    private String productId;
    private String productName;
    private int quantity;
    private String branchId;
    private String branchName;
}
