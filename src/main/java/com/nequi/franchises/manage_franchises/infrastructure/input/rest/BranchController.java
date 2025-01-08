package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.BranchDTO;
import com.nequi.franchises.manage_franchises.application.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Branch API", description = "Manage branches in the system. You can create and disable branches.")
@RestController
@RequestMapping("/api/manage/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @Operation(
            summary = "Create a new branch",
            description = "Creates a new branch for an existing franchise. The branch data must be provided in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid branch data",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.createBranch(branchDTO);
    }

    @Operation(
            summary = "Disable a branch",
            description = "Disables an existing branch by setting its enabled status to false."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch successfully disabled"),
            @ApiResponse(responseCode = "404", description = "Branch not found",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}/disable")
    public Mono<BranchDTO> disableBranch(@PathVariable String id) {
        return branchService.disableBranch(id);
    }
}
