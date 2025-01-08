package com.nequi.franchises.manage_franchises.application.service;

import com.nequi.franchises.manage_franchises.application.dto.BranchDTO;
import com.nequi.franchises.manage_franchises.application.output.port.BranchRepositoryPort;
import com.nequi.franchises.manage_franchises.application.output.port.FranchiseRepositoryPort;
import com.nequi.franchises.manage_franchises.application.service.excepcion.BranchException;
import com.nequi.franchises.manage_franchises.application.service.excepcion.FranchiseException;
import com.nequi.franchises.manage_franchises.application.util.ValidatorUtil;
import com.nequi.franchises.manage_franchises.domain.entity.Branch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {

    private final BranchRepositoryPort branchRepositoryPort;
    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<BranchDTO> createBranch(BranchDTO branchDTO) {
        log.info("Starting branch creation. FranchiseId: {}, BranchName: {}", branchDTO.getFranchiseId(), branchDTO.getName());
        ValidatorUtil.validate(branchDTO);

        return franchiseRepositoryPort.findById(branchDTO.getFranchiseId())
                .switchIfEmpty(Mono.error(new FranchiseException.NotFound()))
                .doOnSuccess(franchise -> log.info("Franchise found. FranchiseId: {}", branchDTO.getFranchiseId()))
                .flatMap(franchise -> validateUniqueBranchName(branchDTO.getName())
                        .then(Mono.defer(() -> {
                            log.info("Branch name is unique. Proceeding with creation. BranchName: {}", branchDTO.getName());
                            Branch branch = new Branch();
                            branch.setName(branchDTO.getName());
                            branch.setAddress(branchDTO.getAddress());
                            branch.setFranchiseId(UUID.fromString(branchDTO.getFranchiseId()));
                            branch.setEnabled(true);
                            branch.setCreatedAt(LocalDateTime.now());

                            return branchRepositoryPort.save(branch)
                                    .doOnSuccess(saved -> log.info("Branch successfully created. BranchId: {}, BranchName: {}", saved.getId(), saved.getName()))
                                    .map(saved -> new BranchDTO(
                                            saved.getId().toString(),
                                            saved.getName(),
                                            saved.getAddress(),
                                            saved.getFranchiseId().toString(),
                                            saved.isEnabled()
                                    ));
                        })));
    }

    private Mono<Void> validateUniqueBranchName(String name) {
        log.info("Validating uniqueness of branch name. BranchName: {}", name);
        return branchRepositoryPort.findByName(name)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Branch name already exists. BranchName: {}", name);
                        return Mono.error(new BranchException.NameAlreadyExists());
                    }
                    log.info("Branch name is unique. BranchName: {}", name);
                    return Mono.empty();
                });
    }

    public Mono<BranchDTO> disableBranch(String id) {
        log.info("Disabling branch. BranchId: {}", id);
        return branchRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .doOnSuccess(branch -> log.info("Branch found for disabling. BranchId: {}", id))
                .flatMap(branch -> {
                    branch.setEnabled(false);
                    branch.setUpdatedAt(LocalDateTime.now());
                    log.info("Updating branch status to disabled. BranchId: {}", id);
                    return branchRepositoryPort.save(branch);
                })
                .doOnSuccess(updated -> log.info("Branch successfully disabled. BranchId: {}", id))
                .map(updated -> new BranchDTO(
                        updated.getId().toString(),
                        updated.getName(),
                        updated.getAddress(),
                        updated.getFranchiseId().toString(),
                        updated.isEnabled()
                ));
    }

    public Mono<BranchDTO> updateBranchName(String id, String newName) {
        log.info("Updating branch name. BranchId: {}, NewName: {}", id, newName);
        if (newName == null || newName.trim().isEmpty()) {
            log.warn("Invalid branch name provided. BranchId: {}, NewName: {}", id, newName);
            return Mono.error(new BranchException.InvalidName());
        }

        return branchRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .doOnSuccess(branch -> log.info("Branch found for name update. BranchId: {}", id))
                .flatMap(branch -> validateUniqueBranchName(newName)
                        .then(Mono.defer(() -> {
                            log.info("Updating branch name. BranchId: {}, NewName: {}", id, newName);
                            branch.setName(newName);
                            branch.setUpdatedAt(LocalDateTime.now());
                            return branchRepositoryPort.save(branch);
                        })))
                .doOnSuccess(updatedBranch -> log.info("Branch name successfully updated. BranchId: {}, NewName: {}", id, newName))
                .map(updatedBranch -> new BranchDTO(
                        updatedBranch.getId().toString(),
                        updatedBranch.getName(),
                        updatedBranch.getAddress(),
                        updatedBranch.getFranchiseId().toString(),
                        updatedBranch.isEnabled()
                ));
    }
}