package com.aireviewassistant.aicodereviewassistant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class OpenAIService {

    private final RestClient restClient;

    public OpenAIService(
            @Value("${openai.api.key}") String apiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String reviewCode(String language, String code, String customPrompt) {
        String prompt = buildReviewPrompt(language, code, customPrompt);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1-mini",
                "input", prompt
        );

        Map response = restClient.post()
                .uri("/responses")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        return extractOutputText(response);
    }

    private String buildReviewPrompt(String language, String code, String customPrompt) {
        String additionalInstructions =
                customPrompt == null || customPrompt.isBlank()
                        ? "Focus on correctness, readability, bugs, edge cases, and maintainability."
                        : customPrompt;

        return """
                You are a senior software engineer.
                Review the following %s code.

                Additional instructions:
                %s

                Return the answer in this format:
                Summary:
                Suggestions:
                Potential bugs:
                Improved version if needed:

                Code:
                ```%s
                %s
                ```
                """.formatted(language, additionalInstructions, language, code);
    }

    private String extractOutputText(Map response) {
        Object outputText = response.get("output_text");

        if (outputText != null) {
            return outputText.toString();
        }

        return response.toString();
    }
}