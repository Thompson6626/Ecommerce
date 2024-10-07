package com.ecommerce.ecommerce.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product , Integer>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
}
