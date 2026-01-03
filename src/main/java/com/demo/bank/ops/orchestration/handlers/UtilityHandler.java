package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.api.dto.UtilityPayRequest;
import com.demo.bank.ops.services.PaymentsClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UtilityHandler implements Handler<UtilityPayRequest> {

  private final PaymentsClient payments;

  public UtilityHandler(PaymentsClient payments) {
    this.payments = payments;
  }

  @Override
  public OperationResult handle(String opId, UtilityPayRequest req) {
    String paymentId = payments.utility(req.customerId(), req.fromAccount(), req.billerCode(), req.billRef(), req.amountMinor(), req.currency(), req.reference());
    return new OperationResult(opId, "ACCEPTED", Map.of("paymentId", paymentId, "mode", "UTILITY"));
  }
}
