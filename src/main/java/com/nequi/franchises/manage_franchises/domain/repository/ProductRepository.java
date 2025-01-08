package com.nequi.franchises.manage_franchises.domain.repository;

import com.nequi.franchises.manage_franchises.domain.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductRepository extends R2dbcRepository<Product, UUID> {

    @Query("SELECT * FROM products WHERE branch_id = :branchId ORDER BY quantity DESC LIMIT 1")
    Flux<Product> findTopStockByBranchId(String branchId);
}
