package com.nequi.franchises.manage_franchises.application.output.port;

import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {
    Mono<Franchise> save(Branch branch);
}
