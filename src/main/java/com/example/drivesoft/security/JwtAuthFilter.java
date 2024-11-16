package com.example.drivesoft.security;

import com.example.drivesoft.user.UserDetailsServiceImpl;
import com.example.drivesoft.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The {@code JwtAuthFilter} class is a Spring Security filter used for validating JWT tokens
 * and setting the authentication context in the security context holder.
 * It extends {@link OncePerRequestFilter} to ensure the filter runs only once per request.
 *
 * <p>This filter extracts the JWT from the "Authorization" header, validates the token,
 * and if valid, sets the user authentication in the security context.</p>
 *
 * <p>If the token is invalid or expired, an error response is returned with the appropriate status
 * and message, indicating the issue with the token.</p>
 *
 * <p>It interacts with the {@link JwtUtil} utility class for extracting the username and validating the token,
 * and the {@link UserDetailsServiceImpl} for loading user details based on the extracted username.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Component} - Marks this class as a Spring component to be automatically discovered and injected by Spring.</li>
 * </ul>
 *
 * @since 1.0
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private JwtUtil jwtUtil;
  private UserDetailsServiceImpl userDetailsService;

  /**
   * Injects dependencies for JwtUtil and UserDetailsServiceImpl.
   *
   * @param jwtUtil            the utility class for JWT handling
   * @param userDetailsService the service for loading user details
   */
  @Autowired
  public void injectDependencies(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Filters the incoming HTTP request to validate the JWT token in the "Authorization" header.
   * If the token is valid, it sets the authentication context in the security context.
   * If the token is invalid or expired, it returns an error response.
   *
   * @param request     the HTTP request
   * @param response    the HTTP response
   * @param filterChain the filter chain to continue the request processing
   * @throws ServletException if an error occurs during the filter chain
   * @throws IOException      if an input or output error occurs
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Retrieve the Authorization header
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;

    try {
      // Check if the header starts with "Bearer "
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7); // Extract token
        username = jwtUtil.extractUsername(token); // Extract username from token
      }
      // If the token is valid and no authentication is set in the context
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate token and set authentication
        if (Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetails))) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (ExpiredJwtException e) {
      // Token expired
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Token has expired");
      return;
    } catch (SignatureException e) {
      // Invalid JWT signature
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid token signature");
      return;
    } catch (Exception e) {
      // Any other exception
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid token");
      return;
    }
    // Continue the filter chain
    filterChain.doFilter(request, response);
  }
}
