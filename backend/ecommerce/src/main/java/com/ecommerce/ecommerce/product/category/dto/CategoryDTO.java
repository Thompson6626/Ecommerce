package com.ecommerce.ecommerce.product.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryDTO(
        @NotBlank(message = "Name must be valid")
        String name,
        @NotBlank(message = "Description must be valid")
        String description
) {
}
