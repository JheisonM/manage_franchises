package com.nequi.franchises.manage_franchises.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("branches")
public class Branch {

    @Id
    private UUID id;
    private String name;
    private String address;

    @Column("franchise_id")
    private UUID franchiseId;

    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}