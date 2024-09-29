package com.ecommerce.ecommerce.review;

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
}
