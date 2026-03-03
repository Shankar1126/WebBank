package com.spring.ex.service;

import com.spring.ex.model.BankCard;
import com.spring.ex.model.Account;
import com.spring.ex.repository.BankCardRepository;
import com.spring.ex.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class CardService {

    private final BankCardRepository cardRepo;
    private final AccountRepository accountRepo;
    private final Random rnd = new Random();

    public CardService(BankCardRepository cardRepo, AccountRepository accountRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
    }


    @Transactional
    public BankCard addDebitCard(String accountId) {
        Optional<Account> acc = accountRepo.findById(accountId);
        if (acc.isEmpty()) throw new IllegalArgumentException("Account not found.");
        String last4 = String.format("%04d", rnd.nextInt(10000));
        BankCard card = new BankCard(last4, "debit", "active", accountId);
        return cardRepo.save(card);
    }


    @Transactional
    public BankCard applyCard(String accountId, String type) {
        Optional<Account> acc = accountRepo.findById(accountId);
        if (acc.isEmpty()) throw new IllegalArgumentException("Account not found.");
        String last4 = String.format("%04d", rnd.nextInt(10000));
        BankCard card = new BankCard(last4, type, "pending", accountId);
        return cardRepo.save(card);
    }

    public List<BankCard> listByAccount(String accountId) {
        return cardRepo.findByAccountId(accountId);
    }
}

