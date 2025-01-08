package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.*;
import com.nequi.franchises.manage_franchises.application.service.ProductService;
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
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToController(productController)
                .build();
    }

    @Test
    void createProduct_Success() {
        // Arrange
        ProductDTO inputDto = new ProductDTO();
        inputDto.setName("Test Product");
        inputDto.setQuantity(10);

        ProductDTO outputDto = new ProductDTO();
        outputDto.setId("1");
        outputDto.setName("Test Product");
        outputDto.setQuantity(10);

        when(productService.createProduct(any(ProductDTO.class)))
                .thenReturn(Mono.just(outputDto));

        // Act & Assert
        webTestClient.post()
                .uri("/api/manage/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDTO.class)
                .isEqualTo(outputDto);

        verify(productService).createProduct(any(ProductDTO.class));
    }

    @Test
    void deleteProduct_Success() {
        // Arrange
        String productId = "1";
        when(productService.deleteProduct(productId))
                .thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/api/manage/products/" + productId)
                .exchange()
                .expectStatus().isNoContent();

        verify(productService).deleteProduct(productId);
    }

    @Test
    void updateProductStock_Success() {
        // Arrange
        String productId = "1";
        ProductStockUpdateDTO updateDTO = new ProductStockUpdateDTO();
        updateDTO.setQuantity(20);

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setId(productId);
        updatedProduct.setQuantity(20);

        when(productService.updateProductStock(eq(productId), eq(20)))
                .thenReturn(Mono.just(updatedProduct));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/products/" + productId + "/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(updatedProduct);

        verify(productService).updateProductStock(productId, 20);
    }

    @Test
    void getTopStockProducts_Success() {
        // Arrange
        String franchiseId = "1";
        ProductStockInfoDTO product1 = new ProductStockInfoDTO();
        ProductStockInfoDTO product2 = new ProductStockInfoDTO();

        when(productService.getTopStockProductsByFranchise(franchiseId))
                .thenReturn(Flux.just(product1, product2));

        // Act & Assert
        webTestClient.get()
                .uri("/api/manage/products/franchise/" + franchiseId + "/top-stock")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductStockInfoDTO.class)
                .hasSize(2)
                .contains(product1, product2);

        verify(productService).getTopStockProductsByFranchise(franchiseId);
    }

    @Test
    void updateProductName_Success() {
        // Arrange
        String productId = "1";
        ProductNameUpdateDTO nameUpdateDTO = new ProductNameUpdateDTO();
        nameUpdateDTO.setName("Updated Product");

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Product");

        when(productService.updateProductName(eq(productId), eq("Updated Product")))
                .thenReturn(Mono.just(updatedProduct));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/manage/products/" + productId + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nameUpdateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(updatedProduct);

        verify(productService).updateProductName(productId, "Updated Product");
    }
}
