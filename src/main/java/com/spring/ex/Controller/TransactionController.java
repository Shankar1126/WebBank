package com.spring.ex.Controller;

import com.spring.ex.dto.ReceiveRequest;
import com.spring.ex.dto.TransferRequest;
import com.spring.ex.model.Transaction;
import com.spring.ex.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService txService;

    public TransactionController(TransactionService txService) {
        this.txService = txService;
    }
    @PostMapping("/transfer")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest req) {
        try {
            Transaction tx = txService.transfer(req.getFromEmail(), req.getToEmail(), req.getAmount(), req.getNote());
            return ResponseEntity.ok(tx);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/receive")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> receive(@Valid @RequestBody ReceiveRequest req) {
        try {
            Transaction tx = txService.receive(req.getToEmail(), req.getAmount(), req.getNote());
            return ResponseEntity.ok(tx);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> list() {
        return ResponseEntity.ok(txService.listAll());
    }
}

