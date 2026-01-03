package com.demo.bank.ops.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo.services")
public record DemoServicesProperties(
    String paymentsBaseUrl,
    String accountBaseUrl,
    String ledgerBaseUrl,
    String limitsBaseUrl,
    String cardsBaseUrl
) {}
