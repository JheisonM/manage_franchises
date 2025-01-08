package com.nequi.franchises.manage_franchises.application.service;

import com.nequi.franchises.manage_franchises.application.dto.FranchiseDTO;
import com.nequi.franchises.manage_franchises.application.output.port.FranchiseRepositoryPort;
import com.nequi.franchises.manage_franchises.application.service.excepcion.FranchiseException;
import com.nequi.franchises.manage_franchises.application.util.ValidatorUtil;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FranchiseService {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<FranchiseDTO> createFranchise(FranchiseDTO franchiseDTO) {
        log.info("Starting franchise creation. FranchiseName: {}", franchiseDTO.getName());
        ValidatorUtil.validate(franchiseDTO);

        Franchise franchise = new Franchise();
        franchise.setName(franchiseDTO.getName());

        return franchiseRepositoryPort.save(franchise)
                .doOnSuccess(saved -> log.info("Franchise successfully created. FranchiseId: {}, FranchiseName: {}", saved.getId(), saved.getName()))
                .map(saved -> new FranchiseDTO(saved.getId().toString(), saved.getName()));
    }

    public Flux<FranchiseDTO> getAllFranchises() {
        log.info("Retrieving all franchises.");
        return franchiseRepositoryPort.findAll()
                .doOnNext(franchise -> log.info("Franchise found. FranchiseId: {}, FranchiseName: {}", franchise.getId(), franchise.getName()))
                .map(franchise -> new FranchiseDTO(franchise.getId().toString(), franchise.getName()));
    }

    public Mono<FranchiseDTO> updateFranchiseName(String id, FranchiseDTO franchiseDTO) {
        log.info("Updating franchise name. FranchiseId: {}, NewName: {}", id, franchiseDTO.getName());
        return franchiseRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new FranchiseException.NotFound()))
                .doOnSuccess(existing -> log.info("Franchise found for update. FranchiseId: {}", id))
                .flatMap(existing -> {
                    existing.setName(franchiseDTO.getName());
                    existing.setUpdatedAt(LocalDateTime.now());
                    log.info("Updating franchise. FranchiseId: {}, NewName: {}", id, franchiseDTO.getName());
                    return franchiseRepositoryPort.save(existing);
                })
                .doOnSuccess(updated -> log.info("Franchise name successfully updated. FranchiseId: {}, UpdatedName: {}", id, franchiseDTO.getName()))
                .map(updated -> new FranchiseDTO(updated.getId().toString(), updated.getName()));
    }

    public Mono<Void> deleteFranchise(String id) {
        log.info("Deleting franchise. FranchiseId: {}", id);
        return franchiseRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new FranchiseException.NotFound()))
                .doOnSuccess(existing -> log.info("Franchise found for deletion. FranchiseId: {}", id))
                .flatMap(existing -> franchiseRepositoryPort.deleteById(id))
                .doOnSuccess(ignored -> log.info("Franchise successfully deleted. FranchiseId: {}", id));
    }
}