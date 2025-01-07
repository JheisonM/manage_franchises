package com.nequi.franchises.manage_franchises.infrastructure.output.adapter.persistence;

import com.nequi.franchises.manage_franchises.application.output.port.FranchiseRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import com.nequi.franchises.manage_franchises.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final FranchiseRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(franchise);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
