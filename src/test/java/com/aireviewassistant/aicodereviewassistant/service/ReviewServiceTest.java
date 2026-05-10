package com.aireviewassistant.aicodereviewassistant.service;

import com.aireviewassistant.aicodereviewassistant.dto.ReviewResponse;
import com.aireviewassistant.aicodereviewassistant.entity.Review;
import com.aireviewassistant.aicodereviewassistant.exception.ReviewNotFoundException;
import com.aireviewassistant.aicodereviewassistant.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void getReviewById_whenReviewExists_returnsReviewResponse() {
        Review review = new Review(
                "def add(a,b): return a+b",
                "Good code",
                "Add spacing and tests",
                "python"
        );

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ReviewResponse response = reviewService.getReviewById(1L);

        assertEquals("Good code", response.getSummary());
        assertEquals("Add spacing and tests", response.getSuggestions());
        assertEquals("python", response.getLanguage());

        verify(reviewRepository).findById(1L);
    }

    @Test
    void getReviewById_whenReviewDoesNotExist_throwsReviewNotFoundException() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ReviewNotFoundException.class,
                () -> reviewService.getReviewById(99L)
        );

        verify(reviewRepository).findById(99L);
    }

    @Test
    void getReviewsByLanguage_whenLanguageProvided_returnsFilteredReviews() {
        Review review = new Review(
                "public int sum(){ return 1; }",
                "Java review",
                "Improve naming",
                "java"
        );

        when(reviewRepository.findByLanguage("java")).thenReturn(List.of(review));

        List<ReviewResponse> responses = reviewService.getReviewsByLanguage("java");

        assertEquals(1, responses.size());
        assertEquals("java", responses.get(0).getLanguage());

        verify(reviewRepository).findByLanguage("java");
        verify(reviewRepository, never()).findAll();
    }

    @Test
    void getReviewsByLanguage_whenLanguageIsNull_returnsAllReviews() {
        Review review = new Review(
                "def test(): pass",
                "Python review",
                "Add tests",
                "python"
        );

        when(reviewRepository.findAll()).thenReturn(List.of(review));

        List<ReviewResponse> responses = reviewService.getReviewsByLanguage(null);

        assertEquals(1, responses.size());
        assertEquals("python", responses.get(0).getLanguage());

        verify(reviewRepository).findAll();
        verify(reviewRepository, never()).findByLanguage(anyString());
    }

    @Test
    void deleteReviewById_whenReviewExists_deletesReview() {
        Review review = new Review(
                "code",
                "summary",
                "suggestions",
                "java"
        );

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReviewById(1L);

        verify(reviewRepository).findById(1L);
        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteReviewById_whenReviewDoesNotExist_throwsReviewNotFoundException() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ReviewNotFoundException.class,
                () -> reviewService.deleteReviewById(99L)
        );

        verify(reviewRepository).findById(99L);
        verify(reviewRepository, never()).delete(any());
    }
}