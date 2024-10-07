package com.ecommerce.ecommerce.review;

import com.ecommerce.ecommerce.exceptions.*;
import com.ecommerce.ecommerce.review.dto.ReviewResponse;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.product.ProductRepository;
import com.ecommerce.ecommerce.review.dto.ReviewRequest;
import com.ecommerce.ecommerce.review.dto.ReviewUpdateRequest;
import com.ecommerce.ecommerce.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;

    public ReviewResponse postReview(ReviewRequest request){
        Review review = reviewMapper.toEntity(request);

        var product = productRepository.findById(request.productId())
                        .orElseThrow(() -> new ProductNotFoundException(
                                            Identifier.ID, 
                                            request.productId())
                                        );

        review.setProduct(product);

        review = reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }
    public ReviewResponse updateReview(
        int reviewId,
        ReviewUpdateRequest request,
        User connectedUser
        ){

        if(connectedUser == null) return ReviewResponse.builder().build();

        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(
                                            Identifier.ID, 
                                            reviewId)
                                        );

        if(!connectedUser.getId().equals(review.getCreatedBy().getId())){
            throw new UnauthorizedModificationException("Cannot modify other user's reviews");
        }

        review.setComment(request.comment());
        review.setRating(request.rating());

        review = reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    public void deleteReview(int reviewId, User connectedUser) {
        if(connectedUser == null) return;

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(
                        Identifier.ID,
                        reviewId)
                );

        // If the id its not the same or the user doesnt have admin role
        if(!connectedUser.getId().equals(review.getCreatedBy().getId()) ||
            connectedUser.getRoles().stream().noneMatch(e -> e.getName().equals("ROLE_ADMIN"))
        ){
            throw new UnauthorizedModificationException("Cannot delete other user's reviews");
        }
        reviewRepository.deleteById(reviewId);
    }
}
