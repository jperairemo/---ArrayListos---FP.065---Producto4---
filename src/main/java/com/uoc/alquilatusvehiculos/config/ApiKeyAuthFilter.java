package com.uoc.alquilatusvehiculos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${api.token}")
    private String apiToken;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Solo protegemos rutas /api/secure/**
        if (!path.startsWith("/api/secure/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String headerToken = request.getHeader("X-API-TOKEN");

        if (headerToken == null || !headerToken.equals(apiToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("API token incorrecto o ausente");
            return;
        }

        // Token correcto, seguimos la cadena
        filterChain.doFilter(request, response);
    }
}
