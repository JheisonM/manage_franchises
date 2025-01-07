package com.nequi.franchises.manage_franchises.domain.repository;

import com.nequi.franchises.manage_franchises.domain.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ProductRepository extends R2dbcRepository<Product, UUID> {

}
