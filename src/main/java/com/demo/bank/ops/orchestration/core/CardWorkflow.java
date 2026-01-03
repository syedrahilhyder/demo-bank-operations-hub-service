package com.demo.bank.ops.orchestration.core;

import com.demo.bank.ops.api.dto.CardAuthAndCaptureRequest;
import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.services.CardsClient;
import com.demo.bank.ops.services.LimitsClient;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CardWorkflow {

  private final CardsClient cardsClient;
  private final LimitsClient limitsClient;

  public CardWorkflow(CardsClient cardsClient, LimitsClient limitsClient) {
    this.cardsClient = cardsClient;
    this.limitsClient = limitsClient;
  }

  public CardContext createContext(String opId, CardAuthAndCaptureRequest req) {
    return new CardContext(opId, req);
  }

  public void execute(CardContext ctx) {
    validateMerchant(ctx);
    enforceLimits(ctx);
    authorize(ctx);
    capture(ctx);
    finalizeStatus(ctx);
  }

  public OperationResult toResult(CardContext ctx) {
    Map<String, Object> details = new LinkedHashMap<>(ctx.details());
    details.put("opId", ctx.opId());
    return new OperationResult(ctx.opId(), ctx.status(), details);
  }

  private void validateMerchant(CardContext ctx) {
    if (ctx.req().merchantId().length() < 2) throw new IllegalArgumentException("invalid merchantId");
    ctx.put("merchantValidated", true);
  }

  private void enforceLimits(CardContext ctx) {
    String pseudoCustomerId = "CARD-" + Math.abs(ctx.req().cardToken().hashCode());
    boolean allowed = limitsClient.check(pseudoCustomerId, ctx.req().amountMinor());
    ctx.put("limitsAllowed", allowed);
    if (!allowed) throw new IllegalStateException("Card limits exceeded");
  }

  private void authorize(CardContext ctx) {
    String authId = cardsClient.authorize(ctx.req().cardToken(), ctx.req().amountMinor(), ctx.req().currency());
    ctx.put("authId", authId);
  }

  private void capture(CardContext ctx) {
    String authId = (String) ctx.get("authId");
    internalCaptureSteps(authId, ctx);
  }

  private void internalCaptureSteps(String authId, CardContext ctx) {
    prepareCapture(authId, ctx);
    performCapture(authId, ctx);
    postCapture(authId, ctx);
  }

  private void prepareCapture(String authId, CardContext ctx) {
    ctx.put("capturePrepared", true);
  }

  private void performCapture(String authId, CardContext ctx) {
    ctx.put("captureDone", true);
  }

  private void postCapture(String authId, CardContext ctx) {
    ctx.put("captureRef", "CAP-" + authId.substring(0, 8));
  }

  private void finalizeStatus(CardContext ctx) {
    ctx.succeed();
  }
}
