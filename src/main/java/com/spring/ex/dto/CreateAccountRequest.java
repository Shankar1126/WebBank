package com.spring.ex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class CreateAccountRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @PositiveOrZero
    private BigDecimal initialDeposit = BigDecimal.ZERO;

    public CreateAccountRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getInitialDeposit() { return initialDeposit; }
    public void setInitialDeposit(BigDecimal initialDeposit) { this.initialDeposit = initialDeposit; }
}