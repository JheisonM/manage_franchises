package com.nequi.franchises.manage_franchises.domain.repository;

import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface BranchRepository extends R2dbcRepository<Branch, UUID> {

}
