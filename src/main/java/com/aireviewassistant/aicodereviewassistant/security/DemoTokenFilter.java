package com.aireviewassistant.aicodereviewassistant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DemoTokenFilter extends OncePerRequestFilter {

    private static final String DEMO_TOKEN_HEADER = "X-Demo-Token";

    @Value("${demo.token:}")
    private String demoToken;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean isReviewsEndpoint = path.startsWith("/api/v1/reviews");
        boolean isMutatingRequest =
                method.equalsIgnoreCase("POST")
                        || method.equalsIgnoreCase("PUT")
                        || method.equalsIgnoreCase("PATCH")
                        || method.equalsIgnoreCase("DELETE");

        return !(isReviewsEndpoint && isMutatingRequest);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (demoToken == null || demoToken.isBlank()) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "status": 503,
                  "message": "Demo token is not configured"
                }
                """);
            return;
        }

        String providedToken = request.getHeader(DEMO_TOKEN_HEADER);

        if (!demoToken.equals(providedToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "status": 401,
                  "message": "Missing or invalid demo token"
                }
                """);
            return;
        }

        filterChain.doFilter(request, response);
    }
}