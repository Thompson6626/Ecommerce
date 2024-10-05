package com.ecommerce.ecommerce.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewResponse(
        int userId,
        String comment,
        int rating,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdDate
) {
}
