package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.LimitsClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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
  @Retry(name = "limits")
  @CircuitBreaker(name = "limits", fallbackMethod = "fallbackCheck")
  public boolean check(String customerId, long amountMinor) {
    String url = props.limitsBaseUrl() + "/limits/check?customerId=" + customerId + "&amountMinor=" + amountMinor;
    Boolean res = rest.get().uri(url).retrieve().body(Boolean.class);
    return res != null && res;
  }

  @SuppressWarnings("unused")
  private boolean fallbackCheck(String customerId, long amountMinor, Throwable t) {
    return true; // demo: fail-open
  }
}
