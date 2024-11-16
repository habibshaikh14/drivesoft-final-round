package com.example.drivesoft.sync;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The {@code SyncScheduler} class is responsible for scheduling and executing synchronization tasks.
 * It ensures that the synchronization process runs both on application startup and at regular intervals.
 * <p>
 * The synchronization task fetches and processes account data from the IDMS system, ensuring the data is up-to-date.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Component} - Marks this class as a Spring component, allowing it to be registered as a bean in the application context.</li>
 * <li>{@code @PostConstruct} - Ensures that the {@code runOnStartupAsync} method is executed once the bean is fully initialized.</li>
 * <li>{@code @Async} - Marks the {@code runOnStartupAsync} method to be executed asynchronously.</li>
 * <li>{@code @Scheduled} - Marks the {@code runSyncTask} method to be executed periodically at a fixed rate (every 15 minutes in this case).</li>
 * </ul>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code runOnStartupAsync} - Runs the initial synchronization task asynchronously when the application starts.</li>
 * <li>{@code runSyncTask} - Schedules the synchronization task to run every 15 minutes.</li>
 * </ul>
 *
 * @since 1.0
 */
@Component
public class SyncScheduler {

  // Service to handle synchronization tasks
  private final SyncService syncService;

  /**
   * Constructs a {@code SyncScheduler} with the required {@code SyncService} dependency.
   *
   * @param syncService the service for performing the synchronization
   */
  public SyncScheduler(SyncService syncService) {
    this.syncService = syncService;
  }

  /**
   * Runs the initial synchronization task asynchronously when the application starts.
   * This method is invoked once after the Spring bean is fully initialized.
   *
   * @see SyncService#sync()
   */
  @PostConstruct
  @Async
  public void runOnStartupAsync() {
    System.out.println("Running initial sync asynchronously...");
    syncService.sync();
  }

  /**
   * Schedules the synchronization task to run at fixed intervals (every 15 minutes).
   * This ensures that the data is kept up-to-date at regular intervals.
   * <p>
   * The synchronization process fetches account data from the IDMS system and saves it to the repository.
   * </p>
   *
   * @see SyncService#sync()
   */
  @Scheduled(fixedRate = 900000) // 900000 ms = 15 minutes
  public void runSyncTask() {
    System.out.println("Starting scheduled sync...");
    syncService.sync();
  }
}
