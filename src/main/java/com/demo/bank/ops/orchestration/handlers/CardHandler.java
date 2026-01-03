package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.CardAuthRequest;
import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.services.CardsClient;
import com.demo.bank.ops.services.LimitsClient;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CardHandler implements Handler<CardAuthRequest> {

  private final CardsClient cards;
  private final LimitsClient limits;

  public CardHandler(CardsClient cards, LimitsClient limits) {
    this.cards = cards;
    this.limits = limits;
  }

  @Override
  public OperationResult handle(String opId, CardAuthRequest req) {
    Map<String, Object> d = new LinkedHashMap<>();
    String pseudoCustomer = "CARD-" + Math.abs(req.cardToken().hashCode());

    if (!limits.check(pseudoCustomer, req.amountMinor())) {
      return new OperationResult(opId, "REJECTED_LIMITS", Map.of("customer", pseudoCustomer));
    }

    String authId = cards.authorize(req.cardToken(), req.amountMinor(), req.currency());
    d.put("authId", authId);
    d.put("merchantId", req.merchantId());
    return new OperationResult(opId, "OK", d);
  }
}
