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
        ValidatorUtil.validate(branchDTO);

        return franchiseRepositoryPort.findById(branchDTO.getFranchiseId())
                .switchIfEmpty(Mono.error(new FranchiseException.NotFound()))
                .flatMap(franchise -> validateUniqueBranchName(branchDTO.getName())
                        .then(Mono.defer(() -> {
                            Branch branch = new Branch();
                            branch.setName(branchDTO.getName());
                            branch.setAddress(branchDTO.getAddress());
                            branch.setFranchiseId(UUID.fromString(branchDTO.getFranchiseId()));
                            branch.setEnabled(true);
                            branch.setCreatedAt(LocalDateTime.now());

                            return branchRepositoryPort.save(branch)
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
        return branchRepositoryPort.findByName(name)
                .hasElement()
                .flatMap(exists -> exists
                        ? Mono.error(new BranchException.NameAlreadyExists())
                        : Mono.empty());
    }

    public Mono<BranchDTO> disableBranch(String id) {
        return branchRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .flatMap(branch -> {
                    branch.setEnabled(false);
                    branch.setUpdatedAt(LocalDateTime.now());
                    return branchRepositoryPort.save(branch);
                })
                .map(updated -> new BranchDTO(
                        updated.getId().toString(),
                        updated.getName(),
                        updated.getAddress(),
                        updated.getFranchiseId().toString(),
                        updated.isEnabled()
                ));
    }
}