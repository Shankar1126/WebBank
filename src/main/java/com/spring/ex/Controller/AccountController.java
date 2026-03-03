package com.spring.ex.Controller;


import com.spring.ex.dto.CreateAccountRequest;
import com.spring.ex.model.Account;
import com.spring.ex.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountRequest req) {
        Account a = accountService.createAccount(req.getName(), req.getEmail(), req.getInitialDeposit());
        return ResponseEntity.created(URI.create("/api/accounts/" + a.getId())).body(a);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Account>> list() {
        return ResponseEntity.ok(accountService.listAccounts());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> getById(@PathVariable String id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

