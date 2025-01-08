package com.nequi.franchises.manage_franchises.domain.repository;

import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchRepository extends R2dbcRepository<Branch, UUID> {

    @Query("SELECT * FROM branches WHERE name = :name LIMIT 1")
    Mono<Branch> findByName(@Param("name") String name);

    @Query("SELECT * FROM branches WHERE franchise_id = :franchiseId")
    Flux<Branch> findByFranchiseId(@Param("franchiseId") UUID franchiseId);
}
