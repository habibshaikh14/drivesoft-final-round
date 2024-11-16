package com.example.drivesoft.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AccountController is a REST controller that handles API requests related to accounts.
 * It provides an endpoint to fetch all account details, with an optional synchronization parameter.
 * <p>
 * This controller interacts with the AccountService to perform the required business logic.
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController {

  // Service layer dependency to handle account-related operations.
  private final AccountService accountService;

  /**
   * Constructs an AccountController with the specified AccountService.
   *
   * @param accountService the service to manage account-related operations
   */
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  /**
   * Handles GET requests to fetch all accounts.
   * <p>
   * Optionally, a `sync` parameter can be passed to indicate whether the accounts should
   * be synchronized before fetching. By default, synchronization is disabled.
   * </p>
   *
   * @param sync a boolean flag to indicate if accounts should be synchronized before fetching; defaults to false
   * @return a ResponseEntity containing a list of AccountDTO objects and an HTTP status code
   */
  @GetMapping("/fetchAll")
  public ResponseEntity<List<AccountDTO>> fetchAllAccounts(
          @RequestParam(value = "sync", required = false, defaultValue = "false") boolean sync
  ) {
    // Fetch all accounts with optional synchronization
    return new ResponseEntity<>(accountService.fetchAllAccounts(sync), HttpStatus.OK);
  }
}
