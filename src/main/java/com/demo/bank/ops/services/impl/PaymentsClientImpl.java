package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.PaymentsClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class PaymentsClientImpl implements PaymentsClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public PaymentsClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public String local(String customerId, String from, String to, long amountMinor, String currency, String reference) {
    return rest.post().uri(props.paymentsBaseUrl() + "/payments/local")
        .body(Map.of("customerId", customerId, "fromAccount", from, "toAccount", to, "amountMinor", amountMinor, "currency", currency, "reference", reference))
        .retrieve().body(String.class);
  }

  @Override
  public String international(String customerId, String from, String iban, String bic, long amountMinor, String currency, String reference) {
    return rest.post().uri(props.paymentsBaseUrl() + "/payments/international")
        .body(Map.of("customerId", customerId, "fromAccount", from, "beneficiaryIban", iban, "swiftBic", bic, "amountMinor", amountMinor, "currency", currency, "reference", reference))
        .retrieve().body(String.class);
  }

  @Override
  public String utility(String customerId, String from, String biller, String billRef, long amountMinor, String currency, String reference) {
    return rest.post().uri(props.paymentsBaseUrl() + "/payments/utility")
        .body(Map.of("customerId", customerId, "fromAccount", from, "billerCode", biller, "billRef", billRef, "amountMinor", amountMinor, "currency", currency, "reference", reference))
        .retrieve().body(String.class);
  }
}
