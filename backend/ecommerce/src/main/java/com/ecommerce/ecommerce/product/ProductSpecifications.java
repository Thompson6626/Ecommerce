package com.ecommerce.ecommerce.product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import com.ecommerce.ecommerce.product.category.Category;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> hasCategory(Category category) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (category == null || category.getName().isBlank()) {
                return criteriaBuilder.conjunction();  // Always true
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();  // Always true
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
    public static Specification<Product> nameStartsWith(String prefix) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (prefix == null || prefix.isEmpty()) {
                return criteriaBuilder.conjunction(); // Always true if no prefix is provided
            }
            String lowerPrefix = prefix.toLowerCase(); // Convert prefix to lowercase
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerPrefix + "%"); // Case-insensitive matching
        };
    }

    public static Specification<Product> hasPriceGreaterThan(BigDecimal price) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.conjunction();  // Always true
            }
            return criteriaBuilder.greaterThan(root.get("price"), price);
        };
    }
}
