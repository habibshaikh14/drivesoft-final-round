package com.example.drivesoft.sync;

import com.example.drivesoft.account.Account;
import com.example.drivesoft.account.AccountRepository;
import com.example.drivesoft.idms.IDMSConnectorService;
import com.example.drivesoft.idms.objects.IDMSAccountListResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The {@code SyncService} class provides functionality for synchronizing account data
 * between the application and an external IDMS system.
 * <p>
 * It is designed to handle account syncing in an asynchronous, transactional manner to ensure
 * that account data is processed and saved efficiently, while preventing multiple concurrent
 * syncs from running at the same time.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Service} - Marks this class as a Spring service component.</li>
 * <li>{@code @Transactional} - Ensures that all database operations are handled within a transaction.</li>
 * <li>{@code @Async} - Marks the method as asynchronous to allow non-blocking execution.</li>
 * </ul>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code sync} - Initiates the synchronization process, ensuring that only one sync is running at a time.</li>
 * <li>{@code processAndSaveAccounts} - Fetches the account data from the IDMS system, processes it, and saves it to the repository.</li>
 * <li>{@code distinctByKey} - A helper method to filter out duplicate accounts based on their {@code acctID}.</li>
 * <li>{@code saveAccounts} - Saves the accounts to the repository in batches to optimize database performance.</li>
 * </ul>
 *
 * @since 1.0
 */
@Service
public class SyncService {

  // Repository to handle account data access operations.
  private final AccountRepository accountRepository;

  // Service to handle communication with the external IDMS system.
  private final IDMSConnectorService idmsConnectorService;

  // Atomic flag to ensure that only one sync operation can occur at a time.
  private final AtomicBoolean isSyncing = new AtomicBoolean(false);

  /**
   * Constructs a {@code SyncService} with the required dependencies.
   *
   * @param accountRepository    the repository for account data operations
   * @param idmsConnectorService the service for interacting with the IDMS system
   */
  public SyncService(AccountRepository accountRepository, IDMSConnectorService idmsConnectorService) {
    this.accountRepository = accountRepository;
    this.idmsConnectorService = idmsConnectorService;
  }

  /**
   * Initiates the synchronization process to fetch account data from the IDMS system and save it.
   * <p>
   * The method is asynchronous, meaning it will run in a separate thread, allowing other tasks
   * to proceed without blocking. It also ensures that only one sync operation can be running at a time
   * by using an atomic flag.
   * </p>
   */
  @Async
  @Transactional
  public void sync() {
    // Ensures that only one sync operation runs at a time
    if (!isSyncing.compareAndSet(false, true)) {
      System.out.println("Sync already in progress");
      return;
    }
    try {
      // Process and save the accounts from IDMS system
      processAndSaveAccounts();
    } catch (Exception e) {
      // Log the error and handle retry logic if necessary
      System.err.println("Sync failed: " + e.getMessage());
    } finally {
      // Reset the syncing flag after the process is complete
      isSyncing.set(false);
    }
  }

  /**
   * Fetches the account data from the IDMS system, processes it to remove duplicates,
   * and saves it to the account repository.
   *
   * @see IDMSConnectorService#getAccountList
   */
  public void processAndSaveAccounts() {
    // Fetch the account list from IDMS system
    IDMSAccountListResponse response = idmsConnectorService.getAccountList();

    // If response contains data, process and save it
    if (response != null && response.getData() != null) {
      // Map the raw data to Account entities and remove duplicates based on acctID
      List<Account> accounts = response.getData().stream()
              .map(wrapper -> wrapper.getRow().mapToEntity())
              .filter(distinctByKey(Account::getAcctID)) // Remove duplicates by acctID
              .toList();

      // Save the accounts to the repository
      saveAccounts(accounts);
    }
  }

  /**
   * Helper method to filter out duplicates based on a specific key (in this case, acctID).
   *
   * @param keyExtractor a function to extract the key from the account entity
   * @param <T>          the type of the object to filter
   * @return a predicate that filters out duplicates
   */
  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  /**
   * Saves a list of account entities to the repository.
   * <p>
   * The accounts are saved in batches to optimize database performance. If an account
   * already exists (checked by acctID), it is skipped.
   * </p>
   *
   * @param accounts the list of accounts to be saved
   */
  private void saveAccounts(List<Account> accounts) {
    int batchSize = 30;
    for (int i = 0; i < accounts.size(); i++) {
      // Skip if the account already exists
      if (accountRepository.existsByAcctID(accounts.get(i).getAcctID())) {
        continue;
      }
      // Save the account to the repository
      accountRepository.save(accounts.get(i));

      // Flush in batches to improve performance
      if (i % batchSize == 0 && i > 0) {
        accountRepository.flush();
      }
    }
    // Final flush to ensure all accounts are persisted
    accountRepository.flush();
  }
}
