package com.example.drivesoft.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code AccountRepository} interface provides data access methods for the {@link Account} entity.
 * <p>
 * This repository extends the {@link JpaRepository}, which provides standard CRUD operations and
 * additional JPA-specific functionality. Custom query methods can also be defined here.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Repository} - Marks this interface as a Spring Data repository.</li>
 * </ul>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code existsByAcctID} - Checks if an account with a specific account ID exists.</li>
 * </ul>
 *
 * @since 1.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  /**
   * Checks if an account exists with the specified account ID.
   *
   * @param acctID the unique account ID to check
   * @return {@code true} if an account with the given account ID exists; otherwise {@code false}
   */
  boolean existsByAcctID(String acctID);
}
