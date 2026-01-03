package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.api.dto.TransferRequest;
import com.demo.bank.ops.services.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransferHandler implements Handler<TransferRequest> {

  private final LimitsClient limits;
  private final AccountClient accounts;
  private final PaymentsClient payments;
  private final LedgerClient ledger;
  private final Compensation compensation;

  public TransferHandler(LimitsClient limits, AccountClient accounts, PaymentsClient payments, LedgerClient ledger, Compensation compensation) {
    this.limits = limits;
    this.accounts = accounts;
    this.payments = payments;
    this.ledger = ledger;
    this.compensation = compensation;
  }

  @Override
  public OperationResult handle(String opId, TransferRequest req) {
    Map<String, Object> d = new LinkedHashMap<>();
    List<Runnable> comp = new ArrayList<>();

    if (!limits.check(req.customerId(), req.amountMinor())) {
      return new OperationResult(opId, "REJECTED_LIMITS", Map.of("reason", "limits"));
    }

    UUID holdId = accounts.hold(req.fromAccount(), req.amountMinor(), "HUB_TRANSFER");
    d.put("holdId", holdId.toString());
    comp.add(() -> accounts.release(holdId));

    try {
      String paymentId = payments.local(req.customerId(), req.fromAccount(), req.toAccount(), req.amountMinor(), req.currency(), req.reference());
      d.put("paymentId", paymentId);

      String ledgerId = ledger.post(paymentId, req.fromAccount(), req.toAccount(), req.amountMinor(), req.currency());
      d.put("ledgerId", ledgerId);

      accounts.release(holdId);
      d.put("holdReleased", true);

      return new OperationResult(opId, "OK", d);
    } catch (Exception e) {
      d.put("error", e.toString());
      compensation.runAll(comp);
      return new OperationResult(opId, "FAILED", d);
    }
  }
}
