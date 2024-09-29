package com.ecommerce.ecommerce.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.category.Category;

public interface ProductRepository extends JpaRepository<Product , Integer>{

    Optional<Product> findByName(String name);
    Page<Product> findByCategory(Category category, Pageable pageable);
}
