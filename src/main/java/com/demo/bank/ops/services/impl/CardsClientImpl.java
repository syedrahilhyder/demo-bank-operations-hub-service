package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.config.DemoServicesProperties;
import com.demo.bank.ops.services.CardsClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CardsClientImpl implements CardsClient {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public CardsClientImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public String authorize(String cardToken, long amountMinor, String currency) {
    String url = props.cardsBaseUrl() + "/cards/authorize?cardToken=" + cardToken + "&amountMinor=" + amountMinor + "&currency=" + currency;
    return rest.post().uri(url).body((Object) null).retrieve().body(String.class);
  }
}
