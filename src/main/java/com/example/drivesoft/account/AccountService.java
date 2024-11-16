package com.example.drivesoft.account;

import com.example.drivesoft.sync.SyncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final SyncService syncService;

  public AccountService(AccountRepository accountRepository, SyncService syncService) {
    this.accountRepository = accountRepository;
    this.syncService = syncService;
  }

  @Transactional(readOnly = true)
  public List<Account> fetchAllAccounts(boolean sync) {
    if (sync) {
      syncService.sync();
    }
    return accountRepository.findAll();
  }
}

