package com.nequi.franchises.manage_franchises.domain.repository;

import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FranchiseRepository extends R2dbcRepository<Franchise, String> {

}
