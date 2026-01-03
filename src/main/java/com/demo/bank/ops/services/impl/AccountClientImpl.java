package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.AccountClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class AccountClientImpl implements AccountClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public AccountClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public void debit(String accountId, long amountMinor) {
    rest.post().uri(props.accountBaseUrl() + "/accounts/" + accountId + "/debit?amountMinor=" + amountMinor)
        .body((Object) null).retrieve().toBodilessEntity();
  }

  @Override
  public void credit(String accountId, long amountMinor) {
    rest.post().uri(props.accountBaseUrl() + "/accounts/" + accountId + "/credit?amountMinor=" + amountMinor)
        .body((Object) null).retrieve().toBodilessEntity();
  }

  @Override
  public UUID hold(String accountId, long amountMinor, String reason) {
    return rest.post().uri(props.accountBaseUrl() + "/accounts/" + accountId + "/holds?amountMinor=" + amountMinor + "&reason=" + reason)
        .body((Object) null).retrieve().body(UUID.class);
  }

  @Override
  public void release(UUID holdId) {
    rest.post().uri(props.accountBaseUrl() + "/accounts/holds/" + holdId + "/release")
        .body((Object) null).retrieve().toBodilessEntity();
  }
}
