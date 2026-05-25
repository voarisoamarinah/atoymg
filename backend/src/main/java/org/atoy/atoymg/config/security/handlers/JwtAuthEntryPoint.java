package org.atoy.atoymg.config.security.handlers;

import java.io.IOException;

import org.atoy.atoymg.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, 
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        // Construire la réponse standardisée
        RestResponse<Void> restResponse = RestResponse.buildErrorResponse(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            null  // Aucune donnée supplémentaire
        );

        // Convertir en JSON
        String jsonResponse = objectMapper.writeValueAsString(restResponse);

        // Configurer la réponse HTTP
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonResponse);
    }
}
