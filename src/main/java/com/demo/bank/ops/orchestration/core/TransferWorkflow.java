package com.demo.bank.ops.orchestration.core;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.api.dto.TransferRequest;
import com.demo.bank.ops.services.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TransferWorkflow {

  private final LimitsClient limitsClient;
  private final AccountClient accountClient;
  private final LedgerClient ledgerClient;
  private final PaymentsClient paymentsClient;
  private final ReferenceGenerator referenceGenerator;

  public TransferWorkflow(
      LimitsClient limitsClient,
      AccountClient accountClient,
      LedgerClient ledgerClient,
      PaymentsClient paymentsClient,
      ReferenceGenerator referenceGenerator
  ) {
    this.limitsClient = limitsClient;
    this.accountClient = accountClient;
    this.ledgerClient = ledgerClient;
    this.paymentsClient = paymentsClient;
    this.referenceGenerator = referenceGenerator;
  }

  public TransferContext createContext(String opId, TransferRequest req) {
    TransferContext ctx = new TransferContext(opId, req);
    ctx.put("railRef", referenceGenerator.generateRailReference(opId));
    return ctx;
  }

  public void execute(TransferContext ctx) {
    preCheck(ctx);
    enforceLimits(ctx);
    initiatePayment(ctx);
    reserveFunds(ctx);
    postLedger(ctx);
    finalizeStatus(ctx);
  }

  public OperationResult toResult(TransferContext ctx) {
    Map<String, Object> details = new LinkedHashMap<>(ctx.details());
    details.put("opId", ctx.opId());
    return new OperationResult(ctx.opId(), ctx.status(), details);
  }

  private void preCheck(TransferContext ctx) {
    basicSanity(ctx);
    currencyRules(ctx);
  }

  private void basicSanity(TransferContext ctx) {
    if (ctx.req().currency().length() != 3) throw new IllegalArgumentException("currency must be ISO3");
  }

  private void currencyRules(TransferContext ctx) {
    ctx.put("currencyValidated", true);
  }

  private void enforceLimits(TransferContext ctx) {
    boolean allowed = limitsClient.check(ctx.req().customerId(), ctx.req().amountMinor());
    ctx.put("limitsAllowed", allowed);
    if (!allowed) {
      ctx.fail("REJECTED_LIMITS");
      throw new IllegalStateException("Limits exceeded");
    }
  }

  private void initiatePayment(TransferContext ctx) {
    String paymentId = paymentsClient.initiateLocal(
        ctx.req().customerId(),
        ctx.req().fromAccount(),
        ctx.req().toAccount(),
        ctx.req().amountMinor(),
        ctx.req().currency()
    );
    ctx.put("paymentId", paymentId);
  }

  private void reserveFunds(TransferContext ctx) {
    accountClient.debit(ctx.req().fromAccount(), ctx.req().amountMinor());
    ctx.put("fundsReserved", true);
  }

  private void postLedger(TransferContext ctx) {
    ledgerClient.post(
        (String) ctx.get("paymentId"),
        ctx.req().fromAccount(),
        ctx.req().toAccount(),
        ctx.req().amountMinor(),
        ctx.req().currency()
    );
    ctx.put("ledgerPosted", true);
  }

  private void finalizeStatus(TransferContext ctx) {
    ctx.succeed();
  }
}
