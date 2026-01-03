package com.demo.bank.ops.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
    @NotBlank String customerId,
    @NotBlank String fromAccount,
    @NotBlank String toAccount,
    @Min(1) long amountMinor,
    @NotBlank String currency,
    @NotNull String reference
) {}
