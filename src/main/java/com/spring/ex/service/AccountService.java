package com.spring.ex.service;

import com.spring.ex.model.Account;
import com.spring.ex.model.Transaction;
import com.spring.ex.repository.AccountRepository;
import com.spring.ex.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(String name, String email, BigDecimal initial) {
        Account acc = new Account(name, email, initial);
        Account saved = accountRepository.save(acc);
        if (initial != null && initial.compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = new Transaction("deposit", initial, null, email, "Initial deposit");
            transactionRepository.save(tx);
        }
        return saved;
    }


    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }


    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }


    public Account save(Account account) {
        return accountRepository.save(account);
    }
}

