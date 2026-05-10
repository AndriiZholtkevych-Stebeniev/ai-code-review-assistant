package com.aireviewassistant.aicodereviewassistant.dto;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long id;
    private String summary;
    private String suggestions;
    private String language;
    private LocalDateTime createdAt;

    public ReviewResponse(Long id, String summary, String suggestions, String language, LocalDateTime createdAt) {
        this.id = id;
        this.summary = summary;
        this.suggestions = suggestions;
        this.language = language;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}