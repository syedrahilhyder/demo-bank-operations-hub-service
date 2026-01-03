package com.demo.bank.ops.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UtilityPayRequest(
    @NotBlank String customerId,
    @NotBlank String fromAccount,
    @NotBlank String billerCode,
    @NotBlank String billRef,
    @Min(1) long amountMinor,
    @NotBlank String currency,
    @NotBlank String reference
) {}
