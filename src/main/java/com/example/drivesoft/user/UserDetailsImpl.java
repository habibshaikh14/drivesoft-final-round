package com.example.drivesoft.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * The {@code UserDetailsImpl} class implements the {@link UserDetails} interface.
 * <p>
 * This class is responsible for representing user-specific data (such as username and password)
 * for use by Spring Security during authentication and authorization processes.
 * </p>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code getAuthorities} - Returns the authorities (roles/permissions) granted to the user.</li>
 * <li>{@code getPassword} - Returns the password of the user.</li>
 * <li>{@code getUsername} - Returns the username (or email) of the user.</li>
 * </ul>
 *
 * @since 1.0
 */
public class UserDetailsImpl implements UserDetails {
  // Username for the user
  private final String username;

  // Password for the user
  private final String password;

  /**
   * Constructs a {@code UserDetailsImpl} instance using a {@link User} entity.
   * <p>
   * This constructor is used to convert a {@link User} entity to a Spring Security-specific
   * {@link UserDetails} object.
   * </p>
   *
   * @param user the user entity to convert to UserDetails
   */
  public UserDetailsImpl(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
  }

  /**
   * Returns the authorities (roles/permissions) granted to the user.
   * <p>
   * In this implementation, no specific authorities are assigned, but this can be extended
   * to include roles or permissions if needed.
   * </p>
   *
   * @return an empty list representing no granted authorities
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(); // No authorities granted in this implementation
  }

  /**
   * Returns the password of the user.
   *
   * @return the password of the user
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Returns the username (or email) of the user.
   *
   * @return the username of the user
   */
  @Override
  public String getUsername() {
    return username;
  }
}
