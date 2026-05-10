package com.aireviewassistant.aicodereviewassistant.controller;

import com.aireviewassistant.aicodereviewassistant.dto.CreateReviewRequest;
import com.aireviewassistant.aicodereviewassistant.dto.ReviewResponse;
import com.aireviewassistant.aicodereviewassistant.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse createReview(@Valid @RequestBody CreateReviewRequest request) {
        return reviewService.createReview(request);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping
    public List<ReviewResponse> getReviews(
            @RequestParam(required = false) String language
    ) {
        return reviewService.getReviewsByLanguage(language);
    }

    @DeleteMapping
    public void deleteAllReviews() {
        reviewService.deleteAllReviews();
    }

    @DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }
}