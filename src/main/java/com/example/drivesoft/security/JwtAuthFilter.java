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

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private JwtUtil jwtUtil;
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  public void injectDependencies(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

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

