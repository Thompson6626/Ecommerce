package com.ecommerce.ecommerce.review.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ReviewUpdateRequest(
    @NotBlank(message = "New comment cannot be blank")
    String comment,
    @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
    @Positive(message = "Rating must be valid")
    Integer rating
) {

}
