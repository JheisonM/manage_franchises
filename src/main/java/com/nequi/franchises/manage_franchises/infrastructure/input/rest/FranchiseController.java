package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.FranchiseDTO;
import com.nequi.franchises.manage_franchises.application.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Franchise API", description = "Manage franchises in the system. You can create, update, delete, and fetch franchises.")
@RestController
@RequestMapping("/api/manage/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Operation(
            summary = "Create a new franchise",
            description = "This endpoint creates a new franchise in the system. The franchise data must be provided in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Franchise successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid franchise data",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseDTO> createFranchise(@RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.createFranchise(franchiseDTO);
    }

    @Operation(
            summary = "Get all franchises",
            description = "Fetches all franchises stored in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of franchises successfully fetched"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping
    public Flux<FranchiseDTO> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    @Operation(
            summary = "Update franchise name",
            description = "Updates the name of an existing franchise by providing the franchise ID and new name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franchise name successfully updated"),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid franchise data",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public Mono<FranchiseDTO> updateFranchiseName(@PathVariable String id, @RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.updateFranchiseName(id, franchiseDTO);
    }

    @Operation(
            summary = "Delete a franchise",
            description = "Deletes an existing franchise by ID from the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Franchise successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFranchise(@PathVariable String id) {
        return franchiseService.deleteFranchise(id);
    }
}
