package com.example.drivesoft.configuration;

import com.example.drivesoft.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The {@code SecurityConfig} class configures security settings for the application.
 * It sets up JWT-based authentication, session management, and access control for
 * different API endpoints.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Configuration} - Marks this class as a Spring configuration class.</li>
 * <li>{@code @EnableWebSecurity} - Enables Spring Security's web security features.</li>
 * </ul>
 *
 * <p>Key Components:</p>
 * <ul>
 * <li>{@code JwtAuthFilter} - A custom filter that processes JWT tokens for user authentication.</li>
 * <li>{@code AuthenticationProvider} - A custom provider to authenticate users.</li>
 * </ul>
 *
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private JwtAuthFilter jwtAuthFilter;
  private AuthenticationProvider authenticationProvider;

  /**
   * Autowires the {@link JwtAuthFilter} bean.
   *
   * @param jwtAuthFilter the JWT authentication filter
   */
  @Autowired
  public void setJwtAuthFilter(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  /**
   * Autowires the {@link AuthenticationProvider} bean.
   *
   * @param authenticationProvider the custom authentication provider
   */
  @Autowired
  public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  /**
   * Configures the {@link SecurityFilterChain} to apply security policies to HTTP requests.
   *
   * <p>Security Configuration:</p>
   * <ul>
   * <li>Disables CSRF protection for stateless APIs.</li>
   * <li>Allows the {@code /auth/login} endpoint without authentication.</li>
   * <li>Requires authentication for all other endpoints.</li>
   * <li>Uses stateless session management to avoid server-side session state.</li>
   * <li>Applies a custom {@link AuthenticationProvider} for user authentication.</li>
   * <li>Sets up custom error handling for unauthorized and forbidden requests.</li>
   * </ul>
   *
   * @param http the {@link HttpSecurity} instance used for configuring security settings
   * @return a configured {@link SecurityFilterChain} instance
   * @throws Exception if any security configuration error occurs
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/login").permitAll() // Allow access to login endpoint
                    .anyRequest().authenticated() // Protect all other endpoints
            )
            .sessionManagement(sess -> sess
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
            )
            .authenticationProvider(authenticationProvider) // Custom authentication provider
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before UsernamePasswordAuthenticationFilter
            .exceptionHandling(
                    exceptionHandling -> exceptionHandling
                            .accessDeniedHandler(
                                    (request, response, accessDeniedException) ->
                                            response.sendError(403, "Forbidden")) // Handle forbidden requests
                            .authenticationEntryPoint(
                                    (request, response, authException) ->
                                            response.sendError(401, "Unauthorized")) // Handle unauthorized requests
            );

    return http.build();
  }
}
