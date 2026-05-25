package org.atoy.atoymg.config.security.handlers;

import java.io.IOException;

import org.atoy.atoymg.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    // Injection de l'ObjectMapper via le constructeur
    public JwtAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        // Création de la réponse standardisée
        RestResponse<Void> restResponse = RestResponse.buildErrorResponse(
            HttpStatus.FORBIDDEN,
            ex.getMessage(),
            null
        );

        // Conversion en JSON
        String jsonResponse = objectMapper.writeValueAsString(restResponse);

        // Configuration de la réponse HTTP
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(jsonResponse);
    }
}
