package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.LedgerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LedgerClientImpl implements LedgerClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public LedgerClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public String post(String paymentId, String debitAccount, String creditAccount, long amountMinor, String currency) {
    String url = props.ledgerBaseUrl() + "/ledger/post?paymentId=" + paymentId
        + "&debitAccount=" + debitAccount + "&creditAccount=" + creditAccount
        + "&amountMinor=" + amountMinor + "&currency=" + currency;
    return rest.post().uri(url).body((Object) null).retrieve().body(String.class);
  }
}
