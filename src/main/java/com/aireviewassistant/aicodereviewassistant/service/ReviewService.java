package com.aireviewassistant.aicodereviewassistant.service;

import com.aireviewassistant.aicodereviewassistant.dto.CreateReviewRequest;
import com.aireviewassistant.aicodereviewassistant.dto.ReviewResponse;
import com.aireviewassistant.aicodereviewassistant.entity.Review;
import com.aireviewassistant.aicodereviewassistant.exception.ReviewNotFoundException;
import com.aireviewassistant.aicodereviewassistant.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OpenAIService openAIService;

    public ReviewService(ReviewRepository reviewRepository, OpenAIService openAIService) {
        this.reviewRepository = reviewRepository;
        this.openAIService = openAIService;
    }

    public ReviewResponse createReview(CreateReviewRequest request) {
        String aiReview = openAIService.reviewCode(
                request.getLanguage(),
                request.getCode(),
                request.getPrompt()
        );

        Review review = new Review(
                request.getCode(),
                aiReview,
                aiReview,
                request.getLanguage()
        );

        Review savedReview = reviewRepository.save(review);

        return mapToResponse(savedReview);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        return mapToResponse(review);
    }

    public List<ReviewResponse> getReviewsByLanguage(String language) {
        List<Review> reviews;

        if (language == null || language.isBlank()) {
            reviews = reviewRepository.findAll();
        } else {
            reviews = reviewRepository.findByLanguage(language);
        }

        return reviews.stream().map(this::mapToResponse).toList();
    }

    public void deleteAllReviews() {
        reviewRepository.deleteAll();
    }

    public void deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        reviewRepository.delete(review);
    }

    private ReviewResponse mapToResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getSummary(),
                review.getSuggestions(),
                review.getLanguage(),
                review.getCreatedAt()
        );
    }
}