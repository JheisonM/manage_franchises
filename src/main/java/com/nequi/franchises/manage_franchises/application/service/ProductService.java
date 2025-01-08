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
        ValidatorUtil.validate(productDTO);

        return branchRepositoryPort.findById(productDTO.getBranchId())
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .flatMap(branch -> {
                    Product product = new Product();
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPricePerUnit(productDTO.getPricePerUnit());
                    product.setBranchId(UUID.fromString(productDTO.getBranchId()));
                    product.setQuantity(productDTO.getQuantity());
                    product.setCreatedAt(LocalDateTime.now());

                    return productRepositoryPort.save(product)
                            .map(saved -> mapToDTO(saved));
                });
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .flatMap(product -> productRepositoryPort.deleteById(id));
    }

    public Mono<ProductDTO> updateProductStock(String id, int newQuantity) {
        if (newQuantity < 0) {
            return Mono.error(new ProductException.InvalidStock());
        }

        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .flatMap(product -> {
                    product.setQuantity(newQuantity);
                    product.setUpdatedAt(LocalDateTime.now());
                    return productRepositoryPort.save(product);
                })
                .map(this::mapToDTO);
    }

    public Flux<ProductStockInfoDTO> getTopStockProductsByFranchise(String franchiseId) {
        return branchRepositoryPort.findByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new BranchException.NotFound()))
                .flatMap(branch ->
                        productRepositoryPort.findTopStockByBranchId(branch.getId().toString())
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
        if (newName == null || newName.trim().isEmpty()) {
            return Mono.error(new ProductException.InvalidName());
        }

        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ProductException.NotFound()))
                .flatMap(product -> {
                    product.setName(newName.trim());
                    product.setUpdatedAt(LocalDateTime.now());
                    return productRepositoryPort.save(product);
                })
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
