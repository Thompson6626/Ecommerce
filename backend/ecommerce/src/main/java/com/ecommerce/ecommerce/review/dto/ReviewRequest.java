package com.ecommerce.ecommerce.review.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ReviewRequest (
    @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
    Integer rating,
    @NotBlank(message = "Comment cannot be blank")
    String comment,
    @Positive(message = "Id must be valid")
    Integer productId
){

}
