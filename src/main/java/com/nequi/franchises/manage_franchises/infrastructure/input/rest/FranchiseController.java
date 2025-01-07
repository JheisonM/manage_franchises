package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.FranchiseDTO;
import com.nequi.franchises.manage_franchises.application.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Franchise API", description = "Manage franchises in the system")
@RestController
@RequestMapping("/api/manage/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Operation(summary = "Create a new franchise")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseDTO> createFranchise(@RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.createFranchise(franchiseDTO);
    }

    @Operation(summary = "Get all franchises")
    @GetMapping
    public Flux<FranchiseDTO> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    @Operation(summary = "Update franchise name")
    @PutMapping("/{id}")
    public Mono<FranchiseDTO> updateFranchiseName(@PathVariable String id, @RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.updateFranchiseName(id, franchiseDTO);
    }

    @Operation(summary = "Delete a franchise")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFranchise(@PathVariable String id) {
        return franchiseService.deleteFranchise(id);
    }
}

