package com.ecommerce.ecommerce.review;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.review.dto.ReviewRequest;
import com.ecommerce.ecommerce.review.dto.ReviewUpdateRequest;
import com.ecommerce.ecommerce.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> postReview(
        @Valid @RequestBody ReviewRequest request
    ){
        reviewService.postReview(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } 

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(
        @PathVariable(name = "id") int id, 
        @RequestBody ReviewUpdateRequest request,
        @AuthenticationPrincipal User connectedUser
    ) {
        reviewService.updateReview(id,request,connectedUser);
        return ResponseEntity.ok().build();
    }



}
