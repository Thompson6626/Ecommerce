package com.ecommerce.ecommerce.product.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Description is necessary")
        String description,
        @DecimalMin(value = "0.0")
        BigDecimal price,
        @NotBlank(message = "You must provide an image")
        String imageUrl,
        @Positive(message = "Stock must be valid")
        Integer stock,
        @NotBlank(message = "Category cannot be blank")
        String category
) {
}
