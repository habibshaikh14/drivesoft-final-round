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

@Configuration
@Component
public class AppConfig {

  // Bean for password encoding using BCrypt
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Password encoding
  }

  // Bean for authentication provider using DAO authentication
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService()); // Set custom UserDetailsService
    authenticationProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder
    return authenticationProvider;
  }

  // Bean for authentication manager
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager(); // Get authentication manager from configuration
  }

  // Bean for custom UserDetailsService implementation
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(); // Ensure UserInfoService implements UserDetailsService
  }

  // Bean for RestTemplate to perform REST operations
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(); // Create a new RestTemplate instance
  }
}
