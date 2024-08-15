package com.pratik.cash_rich_assignment.assignment.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PredefinedHeaderFilter extends OncePerRequestFilter {

    private static final String EXPECTED_HEADER = "cashrich-predefined-header";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiOriginHeader = request.getHeader("X-Api-Origin");

        if ("/api/login".equals(request.getRequestURI()) && !EXPECTED_HEADER.equals(apiOriginHeader)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid API Origin Header");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
