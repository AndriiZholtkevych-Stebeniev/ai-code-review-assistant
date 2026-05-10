package com.aireviewassistant.aicodereviewassistant.dto;
import jakarta.validation.constraints.NotBlank;

public class CreateReviewRequest {
    @NotBlank
    private String language;

    @NotBlank
    private String code;

    private String prompt;

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getPrompt() {
        return prompt;
    }
}
