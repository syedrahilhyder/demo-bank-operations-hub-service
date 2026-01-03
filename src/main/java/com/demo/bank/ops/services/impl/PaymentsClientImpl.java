package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.PaymentsClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PaymentsClientImpl implements PaymentsClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public PaymentsClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  @Retry(name = "payments")
  public String initiateLocal(String customerId, String fromAccount, String toAccount, long amountMinor, String currency) {
    String url = props.paymentsBaseUrl()
        + "/payments/local?customerId=" + customerId
        + "&fromAccount=" + fromAccount
        + "&toAccount=" + toAccount
        + "&amountMinor=" + amountMinor
        + "&currency=" + currency;

    UUID id = rest.post().uri(url).body((Object) null).retrieve().body(UUID.class);
    return id != null ? id.toString() : "PAY-" + UUID.randomUUID();
  }
}
