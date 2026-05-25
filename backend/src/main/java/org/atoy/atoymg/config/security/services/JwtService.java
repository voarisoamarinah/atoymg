package org.atoy.atoymg.config.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
  public static String SECRET;
  public static long EXPIRATION_TIME;
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private long expirationTime;
  
  // Get the signing key for JWT token
  private static Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Extract the expiration date from the token
  public static Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extract a claim from the token
  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extract all claims from the token
  private static Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
    .setSigningKey(getSignKey())
    .build()
    .parseClaimsJws(token)
    .getBody();
  }

  @PostConstruct
  public void init() {
    SECRET = secret;
    EXPIRATION_TIME = expirationTime;
  }

  // Generate token with given user name
  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  // Create a JWT token with specified claims and subject (user name)
  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
    .setClaims(claims)
    .setSubject(userName)
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME)) // Token valid for 30 minutes
    .signWith(getSignKey(), SignatureAlgorithm.HS256)
    .compact();
  }

  // Extract the username from the token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Check if the token is expired
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Validate the token against user details and expiration
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
