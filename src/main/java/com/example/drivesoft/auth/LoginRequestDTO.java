package com.example.drivesoft.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code LoginRequestDTO} class represents the data transfer object (DTO)
 * used for user login requests.
 * <p>
 * This DTO is used to encapsulate the username and password submitted by the user
 * during the login process. It serves as the payload for the login endpoint and
 * is passed from the client to the server to authenticate the user.
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
 * <li>{@code username} - The username of the user attempting to log in.</li>
 * <li>{@code password} - The password of the user attempting to log in.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
  /**
   * The username of the user attempting to log in.
   * <p>
   * This field represents the user's unique identifier (e.g., email or username).
   * </p>
   */
  private String username;

  /**
   * The password of the user attempting to log in.
   * <p>
   * This field holds the user's password which is used for authentication.
   * </p>
   */
  private String password;
}
