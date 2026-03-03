package com.spring.ex.repository;

import com.spring.ex.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankCardRepository extends JpaRepository<BankCard, String> {
    List<BankCard> findByAccountId(String accountId);
}
