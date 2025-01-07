package com.nequi.franchises.manage_franchises.application.service;

import com.nequi.franchises.manage_franchises.application.dto.FranchiseDTO;
import com.nequi.franchises.manage_franchises.application.output.port.FranchiseRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class FranchiseService {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<FranchiseDTO> createFranchise(FranchiseDTO franchiseDTO) {
        Franchise franchise = new Franchise();
        franchise.setName(franchiseDTO.getName());

        return franchiseRepositoryPort.save(franchise)
                .map(saved -> new FranchiseDTO(saved.getId().toString(), saved.getName()));
    }

    public Flux<FranchiseDTO> getAllFranchises() {
        return franchiseRepositoryPort.findAll()
                .map(franchise -> new FranchiseDTO(franchise.getId().toString(), franchise.getName()));
    }

    public Mono<FranchiseDTO> updateFranchiseName(String id, FranchiseDTO franchiseDTO) {
        return franchiseRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setName(franchiseDTO.getName());
                    return franchiseRepositoryPort.save(existing);
                })
                .map(updated -> new FranchiseDTO(updated.getId().toString(), updated.getName()));
    }

    public Mono<Void> deleteFranchise(String id) {
        return franchiseRepositoryPort.deleteById(id);
    }
}
