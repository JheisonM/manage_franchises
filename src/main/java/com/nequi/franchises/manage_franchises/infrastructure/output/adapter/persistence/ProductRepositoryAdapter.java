package com.nequi.franchises.manage_franchises.infrastructure.output.adapter.persistence;

import com.nequi.franchises.manage_franchises.application.output.port.ProductRepositoryPort;
import com.nequi.franchises.manage_franchises.domain.entity.Franchise;
import com.nequi.franchises.manage_franchises.domain.entity.Product;
import com.nequi.franchises.manage_franchises.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository repository;

    @Override
    public Mono<Franchise> save(Product product) {
        return null;
    }
}
