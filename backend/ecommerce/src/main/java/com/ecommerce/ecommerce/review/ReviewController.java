package com.ecommerce.ecommerce.review;

import com.ecommerce.ecommerce.review.dto.ReviewResponse;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.review.dto.ReviewRequest;
import com.ecommerce.ecommerce.review.dto.ReviewUpdateRequest;
import com.ecommerce.ecommerce.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> postReview(
        @Valid @RequestBody ReviewRequest request
    ){
        var response = reviewService.postReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } 

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
        @PathVariable(name = "id") int id, 
        @Valid @RequestBody ReviewUpdateRequest request,
        @AuthenticationPrincipal User connectedUser
    ) {
        var response = reviewService.updateReview(id,request,connectedUser);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(
            @PathVariable(name = "id") int id,
            @AuthenticationPrincipal User connectedUser
    ){
        reviewService.deleteReview(id,connectedUser);
        return ResponseEntity.noContent().build();
    }

}
