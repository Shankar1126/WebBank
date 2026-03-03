package com.spring.ex.dto;

import jakarta.validation.constraints.NotBlank;

public class CardApplyRequest {

    @NotBlank
    private String accountId;

    @NotBlank
    private String type; // debit | credit

    public CardApplyRequest() {}

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
