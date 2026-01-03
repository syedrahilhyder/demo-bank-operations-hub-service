package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.InternationalTransferRequest;
import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.services.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InternationalHandler implements Handler<InternationalTransferRequest> {

  private final LimitsClient limits;
  private final AccountClient accounts;
  private final PaymentsClient payments;

  public InternationalHandler(LimitsClient limits, AccountClient accounts, PaymentsClient payments) {
    this.limits = limits;
    this.accounts = accounts;
    this.payments = payments;
  }

  @Override
  public OperationResult handle(String opId, InternationalTransferRequest req) {
    Map<String, Object> d = new LinkedHashMap<>();
    if (!limits.check(req.customerId(), req.amountMinor())) return new OperationResult(opId, "REJECTED_LIMITS", Map.of());

    UUID holdId = accounts.hold(req.fromAccount(), req.amountMinor(), "HUB_INTL");
    d.put("holdId", holdId.toString());

    String paymentId = payments.international(req.customerId(), req.fromAccount(), req.beneficiaryIban(), req.swiftBic(), req.amountMinor(), req.currency(), req.reference());
    d.put("paymentId", paymentId);
    d.put("holdKept", true);

    return new OperationResult(opId, "PENDING", d);
  }
}
