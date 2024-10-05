package com.ecommerce.ecommerce.review;

import com.ecommerce.ecommerce.review.dto.ReviewResponse;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.review.dto.ReviewRequest;

@Service
public class ReviewMapper {

    public Review toEntity(ReviewRequest request){
        return Review.builder()
                .rating(request.rating())
                .comment(request.comment())
                .build();
    }

    public ReviewResponse toResponse(Review review){
        return ReviewResponse.builder()
                .createdDate(review.getCreatedDate())
                .userId(review.getCreatedBy().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}
