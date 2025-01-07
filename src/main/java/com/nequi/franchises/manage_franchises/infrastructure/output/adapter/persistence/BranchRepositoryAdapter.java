package com.nequi.franchises.manage_franchises.infrastructure.output.adapter.persistence;

import com.nequi.franchises.manage_franchises.application.output.port.BranchRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import com.nequi.franchises.manage_franchises.domain.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private final BranchRepository repository;

    @Override
    public Mono<Franchise> save(Branch branch) {
        return null;
    }
}
