package com.example.drivesoft.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository repository;

  /**
   * Dependency injection via setter method.
   * This allows for easy testing and greater flexibility in configuration.
   *
   * @param repository the user repository
   */
  @Autowired
  public void injectDependencies(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Load user details by username.
   * This method is required by the Spring Security framework.
   *
   * @param username the username (or email) used for authentication
   * @return a UserDetails object containing user credentials and authorities
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
