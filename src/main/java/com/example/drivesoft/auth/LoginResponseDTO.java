package com.example.drivesoft.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code LoginResponseDTO} class represents the data transfer object (DTO)
 * used for the response of the login request.
 * <p>
 * This DTO is used to send back the authenticated user's details, including
 * the username and the generated JWT token. It is returned as the response
 * from the login API endpoint after successful user authentication.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Data} - Generates getters, setters, toString, equals, and hashCode methods.</li>
 * <li>{@code @AllArgsConstructor} - Generates a constructor with parameters for all fields.</li>
 * <li>{@code @NoArgsConstructor} - Generates a default no-argument constructor.</li>
 * </ul>
 *
 * <p>Key Properties:</p>
 * <ul>
 * <li>{@code username} - The username of the authenticated user.</li>
 * <li>{@code token} - The JWT token issued to the user for authentication.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

  /**
   * The username of the authenticated user.
   * <p>
   * This field represents the user's unique identifier (e.g., email or username)
   * that was used for login.
   * </p>
   */
  private String username;

  /**
   * The JWT token issued to the user after successful authentication.
   * <p>
   * This token is used to authenticate the user for subsequent requests.
   * </p>
   */
  private String token;
}
