package org.atoy.atoymg.config.security.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
  private final ConcurrentHashMap<String, Long> invalidatedTokens = new ConcurrentHashMap<>();
  
  public void invalidateToken(String token) {
    // Ajoute le token dans la blacklist avec une durée d'expiration correspondant à sa validité
    long expirationTime = extractExpiration(token);
    invalidatedTokens.put(token, expirationTime);
  }
  
  public boolean isTokenInvalidated(String token) {
    // Vérifie si le token est dans la blacklist
    Long expirationTime = invalidatedTokens.get(token);
    return expirationTime != null && expirationTime > System.currentTimeMillis();
  }
  
  private long extractExpiration(String token) {
    // Méthode pour extraire la date d'expiration du JWT (à implémenter avec JwtService)
    return JwtService.extractExpiration(token).getTime();
  }
  
  // Pour nettoyage automatique (facultatif)
  @Scheduled(fixedRate = 60000) // Chaque minute
  public void cleanExpiredTokens() {
    long now = System.currentTimeMillis();
    invalidatedTokens.entrySet().removeIf(entry -> entry.getValue() < now);
  }
}
