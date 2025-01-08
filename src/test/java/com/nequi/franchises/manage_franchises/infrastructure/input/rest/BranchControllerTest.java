package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.BranchDTO;
import com.nequi.franchises.manage_franchises.application.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToController(branchController)
                .build();
    }

    @Test
    void createBranch_Success() {
        // Arrange
        BranchDTO inputDto = new BranchDTO();
        inputDto.setName("Test Branch");
        inputDto.setFranchiseId("1");

        BranchDTO outputDto = new BranchDTO();
        outputDto.setId("1");
        outputDto.setName("Test Branch");
        outputDto.setFranchiseId("1");
        outputDto.setEnabled(true);

        when(branchService.createBranch(any(BranchDTO.class)))
                .thenReturn(Mono.just(outputDto));

        // Act & Assert
        webTestClient.post()
                .uri("/api/manage/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BranchDTO.class)
                .isEqualTo(outputDto);

        verify(branchService).createBranch(any(BranchDTO.class));
    }

    @Test
    void createBranch_FranchiseNotFound() {
        // Arrange
        BranchDTO inputDto = new BranchDTO();
        inputDto.setName("Test Branch");
        inputDto.setFranchiseId("999");

        when(branchService.createBranch(any(BranchDTO.class)))
                .thenReturn(Mono.error(new RuntimeException("Franchise not found")));

        // Act & Assert
        webTestClient.post()
                .uri("/api/manage/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(branchService).createBranch(any(BranchDTO.class));
    }

    @Test
    void disableBranch_Success() {
        // Arrange
        String branchId = "1";
        BranchDTO disabledBranch = new BranchDTO();
        disabledBranch.setId(branchId);
        disabledBranch.setEnabled(false);

        when(branchService.disableBranch(branchId))
                .thenReturn(Mono.just(disabledBranch));

        // Act & Assert
        webTestClient.put()
                .uri("/api/manage/branches/" + branchId + "/disable")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BranchDTO.class)
                .isEqualTo(disabledBranch);

        verify(branchService).disableBranch(branchId);
    }

    @Test
    void updateBranchName_Success() {
        // Arrange
        String branchId = "1";
        BranchDTO inputDto = new BranchDTO();
        inputDto.setName("Updated Branch");

        BranchDTO outputDto = new BranchDTO();
        outputDto.setId(branchId);
        outputDto.setName("Updated Branch");

        when(branchService.updateBranchName(eq(branchId), eq("Updated Branch")))
                .thenReturn(Mono.just(outputDto));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/branches/" + branchId + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BranchDTO.class)
                .isEqualTo(outputDto);

        verify(branchService).updateBranchName(branchId, "Updated Branch");
    }

    @Test
    void updateBranchName_NotFound() {
        // Arrange
        String branchId = "999";
        BranchDTO inputDto = new BranchDTO();
        inputDto.setName("Updated Branch");

        when(branchService.updateBranchName(eq(branchId), any()))
                .thenReturn(Mono.error(new RuntimeException("Branch not found")));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/branches/" + branchId + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(branchService).updateBranchName(eq(branchId), any());
    }
}
