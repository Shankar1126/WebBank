package com.spring.ex.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ReceiveRequest {

    @NotBlank
    private String toEmail;

    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private String note;

    public ReceiveRequest() {}

    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}

