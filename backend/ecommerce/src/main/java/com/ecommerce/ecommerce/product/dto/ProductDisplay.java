package com.ecommerce.ecommerce.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDisplay(
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Integer stock,
        Double rating,
        String category
) {

}
