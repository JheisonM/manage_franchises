package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.FranchiseDTO;
import com.nequi.franchises.manage_franchises.application.service.FranchiseService;
import com.nequi.franchises.manage_franchises.application.service.excepcion.FranchiseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {

    @Mock
    private FranchiseService franchiseService;

    @InjectMocks
    private FranchiseController franchiseController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToController(franchiseController)
                .build();
    }

    @Test
    void createFranchise_Success() {
        // Arrange
        FranchiseDTO inputDto = new FranchiseDTO();
        inputDto.setName("Test Franchise");

        FranchiseDTO outputDto = new FranchiseDTO();
        outputDto.setId("1");
        outputDto.setName("Test Franchise");

        when(franchiseService.createFranchise(any(FranchiseDTO.class)))
                .thenReturn(Mono.just(outputDto));

        // Act & Assert
        webTestClient.post()
                .uri("/api/manage/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(outputDto);

        verify(franchiseService).createFranchise(any(FranchiseDTO.class));
    }

    @Test
    void getAllFranchises_Success() {
        // Arrange
        FranchiseDTO franchise1 = new FranchiseDTO();
        franchise1.setId("1");
        franchise1.setName("Franchise 1");

        FranchiseDTO franchise2 = new FranchiseDTO();
        franchise2.setId("2");
        franchise2.setName("Franchise 2");

        when(franchiseService.getAllFranchises())
                .thenReturn(Flux.just(franchise1, franchise2));

        // Act & Assert
        webTestClient.get()
                .uri("/api/manage/franchises")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FranchiseDTO.class)
                .hasSize(2)
                .contains(franchise1, franchise2);

        verify(franchiseService).getAllFranchises();
    }

    @Test
    void updateFranchiseName_Success() {
        // Arrange
        String franchiseId = "1";
        FranchiseDTO inputDto = new FranchiseDTO();
        inputDto.setName("Updated Franchise");

        FranchiseDTO outputDto = new FranchiseDTO();
        outputDto.setId(franchiseId);
        outputDto.setName("Updated Franchise");

        when(franchiseService.updateFranchiseName(eq(franchiseId), any(FranchiseDTO.class)))
                .thenReturn(Mono.just(outputDto));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/franchises/" + franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(outputDto);

        verify(franchiseService).updateFranchiseName(eq(franchiseId), any(FranchiseDTO.class));
    }

    @Test
    void updateFranchiseName_NotFound() {
        // Arrange
        String franchiseId = "999";
        FranchiseDTO inputDto = new FranchiseDTO();
        inputDto.setName("Updated Franchise");

        when(franchiseService.updateFranchiseName(eq(franchiseId), any(FranchiseDTO.class)))
                .thenReturn(Mono.error(new FranchiseException.NotFound()));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/franchises/" + franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isNotFound();

        verify(franchiseService).updateFranchiseName(eq(franchiseId), any(FranchiseDTO.class));
    }

    @Test
    void deleteFranchise_Success() {
        // Arrange
        String franchiseId = "1";
        when(franchiseService.deleteFranchise(franchiseId))
                .thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/api/manage/franchises/" + franchiseId)
                .exchange()
                .expectStatus().isNoContent();

        verify(franchiseService).deleteFranchise(franchiseId);
    }

    @Test
    void deleteFranchise_NotFound() {
        // Arrange
        String franchiseId = "999";
        when(franchiseService.deleteFranchise(franchiseId))
                .thenReturn(Mono.error(new RuntimeException("Franchise not found")));

        // Act & Assert
        webTestClient.delete()
                .uri("/api/manage/franchises/" + franchiseId)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(franchiseService).deleteFranchise(franchiseId);
    }
}