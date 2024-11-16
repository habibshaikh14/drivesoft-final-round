package com.example.drivesoft.sync;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

  private final SyncService syncService;

  public SyncScheduler(SyncService syncService) {
    this.syncService = syncService;
  }

  @PostConstruct
  @Async
  public void runOnStartupAsync() {
    System.out.println("Running initial sync asynchronously...");
    syncService.sync();
  }

  // Schedule the task to run every 15 minutes
  @Scheduled(fixedRate = 900000) // 900000 ms = 15 minutes
  public void runSyncTask() {
    System.out.println("Starting scheduled sync...");
    syncService.sync();
  }
}

