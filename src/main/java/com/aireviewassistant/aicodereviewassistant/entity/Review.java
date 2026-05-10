package com.aireviewassistant.aicodereviewassistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    private String language;

    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(String code, String summary, String suggestions, String language) {
        this.code = code;
        this.summary = summary;
        this.suggestions = suggestions;
        this.language = language;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
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