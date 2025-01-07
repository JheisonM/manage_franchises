package com.nequi.franchises.manage_franchises.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("products")
public class Product {

    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal pricePerUnit;

    @Column("branch_id")
    private UUID branchId;

    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}