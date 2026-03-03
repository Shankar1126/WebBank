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
public class TransactionService {

    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository txRepo, AccountRepository accountRepo) {
        this.txRepo = txRepo;
        this.accountRepo = accountRepo;
    }


    public List<Transaction> listAll() {
        return txRepo.findAll();
    }


    @Transactional
    public Transaction transfer(String fromEmail, String toEmail, BigDecimal amount, String note) {
        if (fromEmail.equalsIgnoreCase(toEmail)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        Optional<Account> fromOpt = accountRepo.findByEmail(fromEmail);
        if (fromOpt.isEmpty()) throw new IllegalArgumentException("Source account not found.");
        Optional<Account> toOpt = accountRepo.findByEmail(toEmail);
        if (toOpt.isEmpty()) throw new IllegalArgumentException("Destination account not found.");

        Account from = fromOpt.get();
        Account to = toOpt.get();

        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepo.save(from);
        accountRepo.save(to);

        Transaction tx = new Transaction("transfer", amount, fromEmail, toEmail, note);
        return txRepo.save(tx);
    }


    @Transactional
    public Transaction receive(String toEmail, BigDecimal amount, String note) {
        Optional<Account> toOpt = accountRepo.findByEmail(toEmail);
        if (toOpt.isEmpty()) throw new IllegalArgumentException("Destination account not found.");
        Account to = toOpt.get();
        to.setBalance(to.getBalance().add(amount));
        accountRepo.save(to);

        Transaction tx = new Transaction("receive", amount, null, toEmail, note);
        return txRepo.save(tx);
    }
}

