package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.AccountClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AccountClientImpl implements AccountClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public AccountClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  @Retry(name = "account")
  public void debit(String accountId, long amountMinor) {
    String url = props.accountBaseUrl() + "/accounts/" + accountId + "/debit?amountMinor=" + amountMinor;
    rest.post().uri(url).body((Object) null).retrieve().toBodilessEntity();
  }

  @Override
  @Retry(name = "account")
  public void credit(String accountId, long amountMinor) {
    String url = props.accountBaseUrl() + "/accounts/" + accountId + "/credit?amountMinor=" + amountMinor;
    rest.post().uri(url).body((Object) null).retrieve().toBodilessEntity();
  }
}
