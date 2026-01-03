package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.LimitsClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LimitsClientImpl implements LimitsClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public LimitsClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public boolean check(String customerId, long amountMinor) {
    Boolean ok = rest.get().uri(props.limitsBaseUrl() + "/limits/check?customerId=" + customerId + "&amountMinor=" + amountMinor)
        .retrieve().body(Boolean.class);
    return ok != null && ok;
  }
}
