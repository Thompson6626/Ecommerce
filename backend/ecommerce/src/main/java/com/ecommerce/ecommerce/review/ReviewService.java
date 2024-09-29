package com.ecommerce.ecommerce.review;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.exceptions.ReviewNotFoundException;
import com.ecommerce.ecommerce.product.ProductRepository;
import com.ecommerce.ecommerce.review.dto.ReviewRequest;
import com.ecommerce.ecommerce.review.dto.ReviewUpdateRequest;
import com.ecommerce.ecommerce.user.User;
import com.ecommerce.ecommerce.exceptions.Identifier;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;

    public void postReview(ReviewRequest request){
        var review = reviewMapper.toEntity(request);

        var product = productRepository.findById(request.productId())
                        .orElseThrow(() -> new ProductNotFoundException(
                                            Identifier.ID, 
                                            request.productId())
                                        );

        review.setProduct(product);

        reviewRepository.save(review);
    }
    public void updateReview(
        int reviewId,
        ReviewUpdateRequest request,
        User connectedUser
        ){
            
        var review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(
                                            Identifier.ID, 
                                            reviewId)
                                        );

        review.setComment(request.comment());
        review.setRating(request.rating());

        reviewRepository.save(review);
    }

}
