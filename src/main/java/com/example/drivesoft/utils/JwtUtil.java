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

/**
 * The {@code JwtUtil} class provides utility methods for working with JWT (JSON Web Tokens).
 * <p>
 * This class includes methods for generating, validating, and extracting information from JWT tokens.
 * It also handles token signing using a secret key and ensures proper handling of token expiration.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Component} - Marks this class as a Spring component, making it available for dependency injection.</li>
 * </ul>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code generateToken} - Generates a JWT token for the specified username.</li>
 * <li>{@code validateToken} - Validates the JWT token against user details and checks its expiration.</li>
 * <li>{@code extractUsername} - Extracts the username (subject) from the JWT token.</li>
 * <li>{@code extractExpiration} - Extracts the expiration date from the JWT token.</li>
 * </ul>
 *
 * @since 1.0
 */
@Component
public class JwtUtil {

  // Secret key for signing the JWT tokens
  private static final SecretKey SECRET = Jwts.SIG.HS256.key().build();

  // Token expiration time (30 minutes)
  private static final int EXPIRATION_TIME = 1000 * 60 * 30;

  /**
   * Generates a JWT token for the given username.
   * <p>
   * This method creates a token with a subject (username) and an expiration time.
   * The generated token is signed using the secret key.
   * </p>
   *
   * @param userName the username for which the token is generated
   * @return the generated JWT token as a string
   */
  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  /**
   * Creates a JWT token with the specified claims and subject (username).
   * <p>
   * This method is used internally by {@link #generateToken(String)} to create the token,
   * including claims such as the username, issue date, and expiration time.
   * </p>
   *
   * @param claims   the additional claims to include in the token
   * @param userName the subject (username) of the token
   * @return the generated JWT token as a string
   */
  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
            .claims(claims) // Additional claims
            .subject(userName) // Set the subject (username)
            .issuedAt(new Date()) // Token issuance time
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
            .signWith(SECRET) // Sign the token with the secret key
            .compact();
  }

  /**
   * Extracts the username (subject) from the JWT token.
   * <p>
   * This method retrieves the subject (username) stored in the token's claims.
   * </p>
   *
   * @param token the JWT token
   * @return the extracted username from the token
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the JWT token.
   * <p>
   * This method retrieves the expiration date stored in the token's claims.
   * </p>
   *
   * @param token the JWT token
   * @return the expiration date of the token
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a specific claim from the JWT token using the provided resolver function.
   * <p>
   * This method allows extracting any claim from the JWT token, such as username or expiration date,
   * by passing an appropriate claim resolver function.
   * </p>
   *
   * @param token          the JWT token
   * @param claimsResolver the function used to extract a specific claim from the token
   * @param <T>            the type of the claim being extracted
   * @return the extracted claim of type {@code T}
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the JWT token.
   * <p>
   * This method parses the JWT token and returns all the claims stored within it.
   * </p>
   *
   * @param token the JWT token
   * @return the claims extracted from the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(SECRET) // Verify the token signature
            .build() // Build the parser
            .parseSignedClaims(token) // Parse the token
            .getPayload(); // Get the token payload
  }

  /**
   * Checks if the JWT token has expired.
   * <p>
   * This method compares the current time with the expiration time of the token.
   * </p>
   *
   * @param token the JWT token
   * @return true if the token has expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validates the JWT token against user details and checks if the token is expired.
   * <p>
   * This method validates the token by checking if the username in the token matches the
   * provided user details and if the token is not expired.
   * </p>
   *
   * @param token       the JWT token
   * @param userDetails the user details to validate the token against
   * @return true if the token is valid (username matches and token is not expired), false otherwise
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
