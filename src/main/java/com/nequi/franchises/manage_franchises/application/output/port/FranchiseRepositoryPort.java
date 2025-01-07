package com.nequi.franchises.manage_franchises.application.output.port;

import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {
    Mono<Franchise> save(Franchise franchise);
    Flux<Franchise> findAll();
    Mono<Franchise> findById(String id);
    Mono<Void> deleteById(String id);
}
