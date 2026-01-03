package com.demo.bank.ops.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CardAuthRequest(
    @NotBlank String cardToken,
    @NotBlank String merchantId,
    @Min(1) long amountMinor,
    @NotBlank String currency
) {}
