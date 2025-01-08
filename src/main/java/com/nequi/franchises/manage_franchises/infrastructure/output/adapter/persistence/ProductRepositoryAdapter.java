package com.nequi.franchises.manage_franchises.infrastructure.output.adapter.persistence;

import com.nequi.franchises.manage_franchises.application.output.port.ProductRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Product;
import com.nequi.franchises.manage_franchises.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository repository;

    @Override
    public Mono<Product> save(Product product) {
        return repository.save(product);
    }

    @Override
    public Mono<Product> findById(String id) {
        return repository.findById(UUID.fromString(id));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(UUID.fromString(id));
    }

    @Override
    public Flux<Product> findTopStockByBranchId(String branchId) {
        return repository.findTopStockByBranchId(branchId);
    }
}
