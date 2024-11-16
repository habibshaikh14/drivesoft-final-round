package com.example.drivesoft.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code UserDetailsServiceImpl} class is an implementation of Spring Security's {@link UserDetailsService}.
 * <p>
 * This service is responsible for loading user-specific data for authentication and authorization.
 * It fetches user details from the {@link UserRepository} and converts them into a {@link UserDetails} object.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Service} - Marks this class as a Spring service component, making it eligible for dependency injection.</li>
 * </ul>
 *
 * <p>Key Method:</p>
 * <ul>
 * <li>{@code loadUserByUsername} - Loads user details by username (or email), as required by Spring Security.</li>
 * </ul>
 *
 * @since 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  // Repository to handle user data access operations.
  private UserRepository repository;

  /**
   * Dependency injection via setter method.
   * <p>
   * This method allows Spring to inject the {@link UserRepository} bean, enabling easy testing and greater flexibility
   * in configuration.
   * </p>
   *
   * @param repository the user repository to be injected
   */
  @Autowired
  public void injectDependencies(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Loads user details by username.
   * <p>
   * This method is required by the Spring Security framework to retrieve user-specific data for authentication.
   * It fetches the user from the {@link UserRepository} and converts it into a {@link UserDetails} object.
   * If the user is not found, a {@link UsernameNotFoundException} is thrown.
   * </p>
   *
   * @param username the username (or email) used for authentication
   * @return a {@link UserDetails} object containing the user's credentials and authorities
   * @throws UsernameNotFoundException if the user is not found in the database
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Fetch the user details from the repository
    Optional<User> userDetail = repository.findByUsername(username); // Assuming 'username' refers to email

    // Convert the User entity to a UserDetails object and handle absence of user
    return userDetail.map(UserDetailsImpl::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
