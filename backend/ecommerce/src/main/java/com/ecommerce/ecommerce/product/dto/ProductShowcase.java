package com.ecommerce.ecommerce.product.dto;

import lombok.*;

import java.math.BigDecimal;


@Builder
public record ProductShowcase(
        Integer id,
        String name,
        BigDecimal price,
        String imageUrl,
        Double rating
){

}
