package com.spring.ex.Controller;

import com.spring.ex.dto.CardApplyRequest;
import com.spring.ex.model.BankCard;
import com.spring.ex.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    @PostMapping("/add-debit/{accountId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> addDebit(@PathVariable String accountId) {
        try {
            BankCard card = cardService.addDebitCard(accountId);
            return ResponseEntity.ok(card);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/apply")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> apply(@Valid @RequestBody CardApplyRequest req) {
        try {
            BankCard card = cardService.applyCard(req.getAccountId(), req.getType());
            return ResponseEntity.ok(card);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/by-account/{accountId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<BankCard>> listByAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(cardService.listByAccount(accountId));
    }
}

