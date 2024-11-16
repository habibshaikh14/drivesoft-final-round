package com.example.drivesoft.auth;

import com.example.drivesoft.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code AuthController} class is responsible for handling authentication-related requests.
 * It provides an endpoint for user login, authenticating the user and generating a JWT token upon successful login.
 * <p>
 * This controller relies on Spring Security to authenticate the user and uses a {@link JwtUtil} utility class
 * to generate a JWT token that will be returned to the user for subsequent authenticated requests.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @RestController} - Marks the class as a Spring MVC controller, enabling it to handle HTTP requests.</li>
 * <li>{@code @RequestMapping("/auth")} - Maps the base path of the authentication endpoints.</li>
 * </ul>
 *
 * <p>Key Method:</p>
 * <ul>
 * <li>{@code authenticateAndGetToken} - Authenticates the user and returns a JWT token upon successful login.</li>
 * </ul>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  // Utility class for handling JWT creation
  private final JwtUtil jwtUtil;

  // Spring Security's authentication manager to authenticate the user
  private final AuthenticationManager authenticationManager;

  /**
   * Constructs an {@code AuthController} with the required dependencies.
   *
   * @param jwtUtil               the utility class for generating JWT tokens
   * @param authenticationManager the authentication manager to authenticate users
   */
  public AuthController(
          JwtUtil jwtUtil,
          AuthenticationManager authenticationManager) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Authenticates the user and generates a JWT token upon successful authentication.
   * <p>
   * This method authenticates the user by verifying the provided username and password.
   * If authentication is successful, a JWT token is generated and returned to the user.
   * </p>
   *
   * @param authRequest the login request containing the username and password
   * @return a {@link ResponseEntity} containing the {@link LoginResponseDTO} with the JWT token
   * @throws UsernameNotFoundException if the authentication fails
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> authenticateAndGetToken(@RequestBody LoginRequestDTO authRequest) {
    // Attempt to authenticate the user with the provided credentials
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
    );

    // If authentication is successful, generate the JWT token and return it in the response
    if (authentication.isAuthenticated()) {
      LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
      loginResponseDTO.setUsername(authRequest.getUsername());
      loginResponseDTO.setToken(jwtUtil.generateToken(authRequest.getUsername()));
      return ResponseEntity.ok(loginResponseDTO);
    } else {
      // If authentication fails, throw a UsernameNotFoundException
      throw new UsernameNotFoundException("Invalid user request!");
    }
  }
}
