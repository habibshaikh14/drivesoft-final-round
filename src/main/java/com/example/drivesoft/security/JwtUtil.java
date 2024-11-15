package com.example.drivesoft.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
  private static final SecretKey  SECRET = Jwts.SIG.HS256.key().build();
  private static final int EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes

  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  // Create a JWT token with specified claims and subject (user name)
  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
            .claims(claims)
            .subject(userName)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token valid for 30 minutes
            .signWith(SECRET)
            .compact();
  }

  // Extract the username from the token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extract the expiration date from the token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extract a claim from the token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extract all claims from the token
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token).getPayload();
  }

  // Check if the token is expired
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Validate the token against user details and expiration
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}

