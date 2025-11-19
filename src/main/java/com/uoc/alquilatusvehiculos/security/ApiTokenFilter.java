package com.uoc.alquilatusvehiculos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro muy simple que valida un token en la cabecera X-API-TOKEN
 * solo para las rutas que empiezan por /api/secure/
 *
 * Si el token es correcto → deja pasar la petición.
 * Si es incorrecto o falta → devuelve 401 Unauthorized.
 */
@Component
public class ApiTokenFilter extends OncePerRequestFilter {

    // Token “secreto” que deberéis usar en Postman (cabecera X-API-TOKEN)
    private static final String API_TOKEN_ESPERADO = "Arraylistos_TokenAcceso";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Solo aplicamos el filtro a las rutas /api/secure/**
        if (!path.startsWith("/api/secure/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Leer cabecera
        String tokenRecibido = request.getHeader("X-API-TOKEN");

        if (API_TOKEN_ESPERADO.equals(tokenRecibido)) {
            // Token correcto → seguir con la cadena de filtros
            filterChain.doFilter(request, response);
        } else {
            // Token incorrecto o ausente → 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                      "error": "UNAUTHORIZED",
                      "message": "Token API inválido o ausente. Usa la cabecera X-API-TOKEN."
                    }
                    """);
        }
    }
}
