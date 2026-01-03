package com.demo.bank.ops.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CardAuthAndCaptureRequest(
    @NotBlank String cardToken,
    @Min(1) long amountMinor,
    @NotBlank String currency,
    @NotBlank String merchantId
) {}
