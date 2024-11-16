package com.example.drivesoft.user;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code UserRepository} interface is a Spring Data JPA repository for managing {@link User} entities.
 * <p>
 * This repository provides data access methods to perform CRUD operations on {@link User} entities
 * and includes a custom method for fetching a user by their username.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Repository} - Marks this interface as a Spring repository, making it eligible for Spring's
 * exception translation mechanism.</li>
 * </ul>
 *
 * <p>Key Method:</p>
 * <ul>
 * <li>{@code findByUsername} - Retrieves a {@link User} entity based on the given username.</li>
 * </ul>
 *
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Finds a user by their username.
   * <p>
   * This method uses a custom query method to find a {@link User} entity based on the username. If the user is
   * not found, an {@link Optional} is returned to indicate the absence of the user.
   * </p>
   *
   * @param username the username of the user to be retrieved
   * @return an {@link Optional} containing the user if found, otherwise {@link Optional#empty()}
   */
  Optional<User> findByUsername(@NotEmpty(message = "Username cannot be empty") String username);
}
