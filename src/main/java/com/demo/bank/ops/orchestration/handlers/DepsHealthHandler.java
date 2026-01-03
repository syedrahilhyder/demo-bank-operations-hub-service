package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.services.CardsClient;
import com.demo.bank.ops.services.LimitsClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DepsHealthHandler {

  private final LimitsClient limits;
  private final CardsClient cards;

  public DepsHealthHandler(LimitsClient limits, CardsClient cards) {
    this.limits = limits;
    this.cards = cards;
  }

  public OperationResult handle(String opId) {
    boolean limitsOk = limits.check("probe", 1);
    String cardsProbe = safeCardsProbe();
    return new OperationResult(opId, "OK", Map.of("limitsOk", limitsOk, "cardsProbe", cardsProbe));
  }

  private String safeCardsProbe() {
    try {
      return cards.authorize("probe-token", 1, "AED");
    } catch (Exception e) {
      return "FAILED:" + e.getClass().getSimpleName();
    }
  }
}
