package com.nequi.franchises.manage_franchises.application.output.port;

import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(String id);
    Mono<Branch> findByName(String name);
    Flux<Branch> findByFranchiseId(String franchiseId);
}
