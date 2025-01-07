package com.nequi.franchises.manage_franchises.application.output.port;

import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import com.nequi.franchises.manage_franchises.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {
    Mono<Franchise> save(Product product);
}
