package com.nequi.franchises.manage_franchises.application.output.port;

import com.nequi.franchises.manage_franchises.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {
    Mono<Product> save(Product product);
    Mono<Product> findById(String id);
    Mono<Void> deleteById(String id);
    Flux<Product> findTopStockByBranchId(String branchId);
}
