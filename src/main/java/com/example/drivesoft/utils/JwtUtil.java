package com.example.drivesoft.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
  // Secret key for signing the JWT tokens
  private static final SecretKey SECRET = Jwts.SIG.HS256.key().build();
  // Token expiration time (30 minutes)
  private static final int EXPIRATION_TIME = 1000 * 60 * 30;

  /**
   * Generate a JWT token for the given username.
   *
   * @param userName the username for which the token is generated
   * @return the generated JWT token as a string
   */
  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  /**
   * Create a JWT token with specified claims and subject (username).
   *
   * @param claims   additional claims to include in the token
   * @param userName the subject (username) of the token
   * @return the generated JWT token as a string
   */
  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
            .claims(claims) // Additional data for the token
            .subject(userName) // Set the subject (username)
            .issuedAt(new Date()) // Token issuance time
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
            .signWith(SECRET) // Sign the token with the secret key
            .compact();
  }

  /**
   * Extract the username (subject) from the token.
   *
   * @param token the JWT token
   * @return the extracted username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract the expiration date from the token.
   *
   * @param token the JWT token
   * @return the extracted expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extract a specific claim from the token using a resolver function.
   *
   * @param token          the JWT token
   * @param claimsResolver a function to extract a specific claim
   * @param <T>            the type of the claim
   * @return the extracted claim
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extract all claims from the token.
   *
   * @param token the JWT token
   * @return the claims extracted from the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(SECRET) // Verify the signature
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  /**
   * Check if the token is expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validate the token against user details and check if it is expired.
   *
   * @param token       the JWT token
   * @param userDetails the user details to validate against
   * @return true if the token is valid, false otherwise
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}


