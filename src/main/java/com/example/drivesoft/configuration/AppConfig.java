package com.example.drivesoft.configuration;

import com.example.drivesoft.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The {@code AppConfig} class is a configuration class that sets up beans
 * required for authentication, password encoding, and other application-wide
 * settings.
 * <p>
 * This configuration is part of the Spring Boot application context and is
 * responsible for initializing various beans related to security and external
 * service communication.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Configuration} - Marks this class as a source of bean definitions for the application context.</li>
 * <li>{@code @Component} - Indicates that this class is a Spring-managed component.</li>
 * </ul>
 *
 * <p>Key Beans Defined:</p>
 * <ul>
 * <li>{@code passwordEncoder} - Provides a {@link PasswordEncoder} bean used for securely encoding passwords.</li>
 * <li>{@code authenticationProvider} - Defines an {@link AuthenticationProvider} using DAO authentication.</li>
 * <li>{@code authenticationManager} - Exposes an {@link AuthenticationManager} for authentication.</li>
 * <li>{@code userDetailsService} - Provides a custom {@link UserDetailsService} bean for user authentication.</li>
 * <li>{@code restTemplate} - Provides a {@link RestTemplate} for performing REST operations.</li>
 * </ul>
 *
 * @since 1.0
 */
@Configuration
@Component
public class AppConfig {

  /**
   * Creates a {@link PasswordEncoder} bean using BCrypt algorithm.
   * <p>
   * This encoder is used to securely hash and verify passwords during authentication.
   * </p>
   *
   * @return a {@link PasswordEncoder} instance using BCrypt
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Password encoding
  }

  /**
   * Creates an {@link AuthenticationProvider} bean for user authentication.
   * <p>
   * This provider uses a custom {@link UserDetailsService} and a {@link PasswordEncoder}
   * for authenticating users based on credentials stored in the database.
   * </p>
   *
   * @return a configured {@link AuthenticationProvider} instance
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService()); // Set custom UserDetailsService
    authenticationProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder
    return authenticationProvider;
  }

  /**
   * Creates an {@link AuthenticationManager} bean for handling authentication requests.
   * <p>
   * The {@link AuthenticationManager} manages the authentication process by verifying user credentials.
   * </p>
   *
   * @param config {@link AuthenticationConfiguration} for configuring the authentication manager
   * @return an {@link AuthenticationManager} instance
   * @throws Exception if an error occurs while creating the authentication manager
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager(); // Get authentication manager from configuration
  }

  /**
   * Creates a custom {@link UserDetailsService} bean used for loading user details
   * during the authentication process.
   * <p>
   * The {@link UserDetailsService} implementation retrieves user information from the database
   * based on the provided username.
   * </p>
   *
   * @return a custom {@link UserDetailsService} implementation
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(); // Ensure UserInfoService implements UserDetailsService
  }

  /**
   * Creates a {@link RestTemplate} bean to allow communication with external REST APIs.
   * <p>
   * {@link RestTemplate} provides an easy way to perform HTTP requests and handle
   * responses in a Spring Boot application.
   * </p>
   *
   * @return a {@link RestTemplate} instance for making HTTP requests
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(); // Create a new RestTemplate instance
  }
}
