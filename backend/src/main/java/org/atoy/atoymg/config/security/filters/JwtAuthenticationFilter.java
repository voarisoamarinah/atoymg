package org.atoy.atoymg.config.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.atoy.atoymg.config.security.services.JwtService;
import org.atoy.atoymg.config.security.services.TokenBlacklistService;
import org.atoy.atoymg.config.security.services.UserInfoService;
import org.atoy.atoymg.dto.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final TokenBlacklistService tokenBlacklistService;
  private final UserInfoService userInfoService;
  
  private final ObjectMapper objectMapper;
  
  @Autowired
  public JwtAuthenticationFilter(JwtService jwtService, TokenBlacklistService tokenBlacklistService, @Lazy UserInfoService userInfoService,
  ObjectMapper objectMapper) {
    this.jwtService = jwtService;
    this.tokenBlacklistService = tokenBlacklistService;
    this.userInfoService = userInfoService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
  throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);

      // Vérification blacklist
      if (tokenBlacklistService.isTokenInvalidated(jwt)) {
        sendErrorResponse(response,
        HttpStatus.UNAUTHORIZED,
        "Token has been invalidated");
        return;
      }

      try {
        username = jwtService.extractUsername(jwt);
      } catch (Exception e) {
        sendErrorResponse(response,
          HttpStatus.UNAUTHORIZED,
          "Invalid token: " + e.getMessage());
        return;
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userInfoService.loadUserByUsername(username);
        
        try {
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            sendErrorResponse(response, 
                             HttpStatus.UNAUTHORIZED, 
                             "Token validation failed: " + e.getMessage());
            return;
        }
    }
    filterChain.doFilter(request, response);
  }

  private void sendErrorResponse(HttpServletResponse response, 
                                HttpStatus status, 
                                String message) throws IOException {
      RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
          status,
          message,
          null
      );
      
      response.setStatus(status.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
