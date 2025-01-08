package com.nequi.franchises.manage_franchises.infrastructure.input.rest;

import com.nequi.franchises.manage_franchises.application.dto.ProductDTO;
import com.nequi.franchises.manage_franchises.application.dto.ProductNameUpdateDTO;
import com.nequi.franchises.manage_franchises.application.dto.ProductStockInfoDTO;
import com.nequi.franchises.manage_franchises.application.dto.ProductStockUpdateDTO;
import com.nequi.franchises.manage_franchises.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Product API", description = "Manage products in the system")
@RestController
@RequestMapping("/api/manage/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product for a specific branch"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes an existing product by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @Operation(
            summary = "Update product stock",
            description = "Updates the quantity of an existing product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid stock quantity")
    })
    @PatchMapping("/{id}/stock")
    public Mono<ProductDTO> updateProductStock(
            @PathVariable String id,
            @RequestBody ProductStockUpdateDTO updateDTO) {
        return productService.updateProductStock(id, updateDTO.getQuantity());
    }

    @Operation(
            summary = "Get top stock products by franchise",
            description = "Returns a list of products with highest stock for each branch in a franchise"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    @GetMapping("/franchise/{franchiseId}/top-stock")
    public Flux<ProductStockInfoDTO> getTopStockProducts(@PathVariable String franchiseId) {
        return productService.getTopStockProductsByFranchise(franchiseId);
    }

    @Operation(
            summary = "Update product name",
            description = "Updates the name of an existing product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product name successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product name")
    })
    @PatchMapping("/{id}/name")
    public Mono<ProductDTO> updateProductName(
            @PathVariable String id,
            @RequestBody ProductNameUpdateDTO nameUpdateDTO) {
        return productService.updateProductName(id, nameUpdateDTO.getName());
    }
}
