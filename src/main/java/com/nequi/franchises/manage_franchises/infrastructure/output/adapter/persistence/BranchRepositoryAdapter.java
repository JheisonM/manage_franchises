package com.nequi.franchises.manage_franchises.infrastructure.output.adapter.persistence;

import com.nequi.franchises.manage_franchises.application.output.port.BranchRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import com.nequi.franchises.manage_franchises.domain.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private final BranchRepository repository;

    @Override
    public Mono<Branch> save(Branch branch) {
        return repository.save(branch);
    }

    @Override
    public Mono<Branch> findById(String id) {
        return repository.findById(UUID.fromString(id));
    }

    @Override
    public Mono<Branch> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Flux<Branch> findByFranchiseId(String franchiseId) {
        return repository.findByFranchiseId(UUID.fromString(franchiseId));
    }
}
