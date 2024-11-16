package com.example.drivesoft.account;

import com.example.drivesoft.sync.SyncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The {@code AccountService} class provides business logic for managing account-related operations.
 * <p>
 * This service layer bridges the gap between the controller and repository layers, ensuring that
 * operations are performed with the required business rules and transactional behavior.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Service} - Marks this class as a Spring service component.</li>
 * <li>{@code @Transactional} - Ensures proper transaction management for database operations.</li>
 * </ul>
 *
 * <p>Key Method:</p>
 * <ul>
 * <li>{@code fetchAllAccounts} - Fetches all accounts with optional synchronization.</li>
 * </ul>
 *
 * @since 1.0
 */
@Service
public class AccountService {

  // Repository to handle account data access operations.
  private final AccountRepository accountRepository;

  // Service to handle synchronization tasks.
  private final SyncService syncService;

  /**
   * Constructs an {@code AccountService} with the required dependencies.
   *
   * @param accountRepository the repository for account data operations
   * @param syncService       the service for synchronization tasks
   */
  public AccountService(AccountRepository accountRepository, SyncService syncService) {
    this.accountRepository = accountRepository;
    this.syncService = syncService;
  }

  /**
   * Fetches all accounts from the repository, optionally performing a synchronization
   * before retrieving the data.
   * <p>
   * If the {@code sync} parameter is {@code true}, a synchronization operation
   * is triggered via the {@link SyncService} before fetching the accounts.
   * </p>
   *
   * @param sync a boolean flag to indicate whether to perform synchronization before fetching accounts
   * @return a list of {@link AccountDTO} objects representing the account data
   */
  @Transactional(readOnly = true)
  public List<AccountDTO> fetchAllAccounts(boolean sync) {
    // Perform synchronization if the sync flag is true
    if (sync) {
      syncService.sync();
    }
    // Retrieve all accounts and map them to DTOs
    return accountRepository.findAll().stream().map(Account::toDTO).toList();
  }
}
