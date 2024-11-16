package com.example.drivesoft.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/fetchAll")
  public ResponseEntity<List<Account>> fetchAllAccounts(
          @RequestParam(value = "sync", required = false, defaultValue = "false") boolean sync
  ) {
    return new ResponseEntity<>(accountService.fetchAllAccounts(sync), HttpStatus.OK);
  }
}
