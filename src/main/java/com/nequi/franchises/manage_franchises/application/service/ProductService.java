package com.nequi.franchises.manage_franchises.application.service;

import com.nequi.franchises.manage_franchises.application.dto.ProductDTO;
import com.nequi.franchises.manage_franchises.application.dto.ProductStockInfoDTO;
import com.nequi.franchises.manage_franchises.application.output.port.ProductRepositoryPort;
import com.nequi.franchises.manage_franchises.application.output.port.BranchRepositoryPort;
import com.nequi.franchises.manage_franchises.application.service.excepcion.BranchException;
import com.nequi.franchises.manage_franchises.application.service.excepcion.ProductException;
import com.nequi.franchises.manage_franchises.application.util.ValidatorUtil;
import com.nequi.franchises.manage_franchises.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepositoryPort productRepositoryPort;
    private final BranchRepositoryPort branchRepositoryPort;

    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        log.info("Starting product creation. ProductName: {}, BranchId: {}", productDTO.getName(), productDTO.getBranchId());
        ValidatorUtil.validate(productDTO);

        return branchRepositoryPort.findById(productDTO.getBranchId())
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .doOnSuccess(branch -> log.info("Branch found for product creation. BranchId: {}", productDTO.getBranchId()))
                .flatMap(branch -> {
                    Product product = new Product();
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPricePerUnit(productDTO.getPricePerUnit());
                    product.setBranchId(UUID.fromString(productDTO.getBranchId()));
                    product.setQuantity(productDTO.getQuantity());
                    product.setCreatedAt(LocalDateTime.now());

                    return productRepositoryPort.save(product)
                            .doOnSuccess(saved -> log.info("Product successfully created. ProductId: {}, ProductName: {}", saved.getId(), saved.getName()))
                            .map(this::mapToDTO);
                });
    }

    public Mono<Void> deleteProduct(String id) {
        log.info("Starting product deletion. ProductId: {}", id);
        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .doOnSuccess(product -> log.info("Product found for deletion. ProductId: {}", id))
                .flatMap(product -> productRepositoryPort.deleteById(id))
                .doOnSuccess(ignored -> log.info("Product successfully deleted. ProductId: {}", id));
    }

    public Mono<ProductDTO> updateProductStock(String id, int newQuantity) {
        log.info("Updating product stock. ProductId: {}, NewQuantity: {}", id, newQuantity);
        if (newQuantity < 0) {
            log.warn("Invalid stock quantity provided. ProductId: {}, Quantity: {}", id, newQuantity);
            return Mono.error(new ProductException.InvalidStock());
        }

        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .doOnSuccess(product -> log.info("Product found for stock update. ProductId: {}", id))
                .flatMap(product -> {
                    product.setQuantity(newQuantity);
                    product.setUpdatedAt(LocalDateTime.now());
                    log.info("Updating stock for ProductId: {}, Quantity: {}", id, newQuantity);
                    return productRepositoryPort.save(product);
                })
                .doOnSuccess(updated -> log.info("Product stock successfully updated. ProductId: {}, UpdatedQuantity: {}", id, updated.getQuantity()))
                .map(this::mapToDTO);
    }

    public Flux<ProductStockInfoDTO> getTopStockProductsByFranchise(String franchiseId) {
        log.info("Retrieving top stock products by franchise. FranchiseId: {}", franchiseId);
        return branchRepositoryPort.findByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .doOnNext(branch -> log.info("Branch found for franchise. FranchiseId: {}, BranchId: {}", franchiseId, branch.getId()))
                .flatMap(branch -> productRepositoryPort.findTopStockByBranchId(branch.getId().toString())
                        .doOnNext(product -> log.info("Top stock product found. ProductId: {}, BranchId: {}, Quantity: {}", product.getId(), branch.getId(), product.getQuantity()))
                        .map(product -> new ProductStockInfoDTO(
                                product.getId().toString(),
                                product.getName(),
                                product.getQuantity(),
                                branch.getId().toString(),
                                branch.getName()
                        ))
                );
    }

    public Mono<ProductDTO> updateProductName(String id, String newName) {
        log.info("Updating product name. ProductId: {}, NewName: {}", id, newName);
        if (newName == null || newName.trim().isEmpty()) {
            log.warn("Invalid product name provided. ProductId: {}", id);
            return Mono.error(new ProductException.InvalidName());
        }

        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .doOnSuccess(product -> log.info("Product found for name update. ProductId: {}", id))
                .flatMap(product -> {
                    product.setName(newName.trim());
                    product.setUpdatedAt(LocalDateTime.now());
                    log.info("Updating name for ProductId: {}, NewName: {}", id, newName.trim());
                    return productRepositoryPort.save(product);
                })
                .doOnSuccess(updated -> log.info("Product name successfully updated. ProductId: {}, UpdatedName: {}", id, updated.getName()))
                .map(this::mapToDTO);
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getPricePerUnit(),
                product.getBranchId().toString(),
                product.getQuantity()
        );
    }
}