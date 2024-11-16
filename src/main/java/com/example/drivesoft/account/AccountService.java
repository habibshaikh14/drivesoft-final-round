package com.example.drivesoft.account;

import com.example.drivesoft.idms.IDMSConnectorService;
import com.example.drivesoft.idms.objects.IDMSAccountListResponse;
import com.example.drivesoft.idms.objects.IDMSAccountRow;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final IDMSConnectorService idmsConnectorService;

  public AccountService(AccountRepository accountRepository, IDMSConnectorService idmsConnectorService) {
    this.accountRepository = accountRepository;
    this.idmsConnectorService = idmsConnectorService;
  }

  // Helper method to ensure uniqueness by acct_id
  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  public List<Account> getAllAccounts() {
    processAndSaveAccounts(idmsConnectorService.getAccountList());
    return accountRepository.findAll();
  }

  private void processAndSaveAccounts(IDMSAccountListResponse response) {
    if (response != null && response.getData() != null) {
      List<Account> accounts = response.getData().stream()
              .map(wrapper -> mapToEntity(wrapper.getRow()))
              .filter(distinctByKey(Account::getAcctID)) // Filters out duplicates based on acct_id
              .toList();
      saveAccounts(accounts);
    }
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

  private Account mapToEntity(IDMSAccountRow row) {
    Account account = new Account();
    account.setContractSalesPrice(getSalesPrice(row.getContractSalesPrice()));
    account.setAcctType(row.getAcctType());
    account.setSalesGroupPerson1ID(row.getSalesGroupPerson1ID());
    account.setContractDate(parseDate(row.getContractDate()));
    account.setCollateralStockNumber(row.getCollateralStockNumber());
    account.setCollateralYearModel(row.getCollateralYearModel());
    account.setCollateralMake(row.getCollateralMake());
    account.setCollateralModel(row.getCollateralModel());
    account.setBorrower1FirstName(row.getBorrower1FirstName());
    account.setBorrower1LastName(row.getBorrower1LastName());
    account.setAcctID(row.getAcctID());
    return account;
  }

  private BigDecimal getSalesPrice(String salesPrice) {
    BigDecimal price = null;
    try {
      price = new BigDecimal(salesPrice);
    } catch (Exception e) {
      // Handle exception
    }
    return price;
  }

  private LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    LocalDate parsedDate = null;
    try {
      parsedDate = LocalDate.parse(date, formatter);
    } catch (Exception e) {
      // Handle exception
    }
    return parsedDate;
  }
}

