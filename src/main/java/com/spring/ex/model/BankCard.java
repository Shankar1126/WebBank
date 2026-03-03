package com.spring.ex.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class BankCard {
    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(length = 4)
    private String last4;

    private String type;

    // ✅ ADD THIS LINE:
    private String status;

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    private String accountId;

    public BankCard() {}

    public BankCard(String last4, String type, String status, String accountId) {
        this.last4 = last4;
        this.type = type;
        this.status = status; // This will work now!
        this.accountId = accountId;
    }

    // ... (rest of your getters and setters are already correct)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLast4() { return last4; }
    public void setLast4(String last4) { this.last4 = last4; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}