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

@Service
public class SyncService {

  private final AccountRepository accountRepository;
  private final IDMSConnectorService idmsConnectorService;
  private final AtomicBoolean isSyncing = new AtomicBoolean(false);

  public SyncService(AccountRepository accountRepository, IDMSConnectorService idmsConnectorService) {
    this.accountRepository = accountRepository;
    this.idmsConnectorService = idmsConnectorService;
  }

  @Async
  @Transactional
  public void sync() {
    if (!isSyncing.compareAndSet(false, true)) {
      System.out.println("Sync already in progress");
      return;
    }
    try {
      processAndSaveAccounts();
    } catch (Exception e) {
      // Log the error and retry logic (or notify administrators)
      System.err.println("Sync failed: " + e.getMessage());
      // Optionally retry the process here
    } finally {
      isSyncing.set(false);
    }
  }

  public void processAndSaveAccounts() {
    IDMSAccountListResponse response = idmsConnectorService.getAccountList();
    if (response != null && response.getData() != null) {
      List<Account> accounts = response.getData().stream()
              .map(wrapper -> wrapper.getRow().mapToEntity())
              .filter(distinctByKey(Account::getAcctID)) // Filters out duplicates based on acct_id
              .toList();
      saveAccounts(accounts);
    }
  }

  // Helper method to ensure uniqueness by acct_id
  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  private void saveAccounts(List<Account> accounts) {
    int batchSize = 30;
    for (int i = 0; i < accounts.size(); i++) {
      if (accountRepository.existsByAcctID(accounts.get(i).getAcctID())) {
        continue;
      }
      accountRepository.save(accounts.get(i));
      if (i % batchSize == 0 && i > 0) {
        accountRepository.flush();
      }
    }
    accountRepository.flush();
  }
}
