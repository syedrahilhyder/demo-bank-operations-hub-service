package com.demo.bank.ops.orchestration;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.api.dto.TransferRequest;
import com.demo.bank.ops.orchestration.core.TransferWorkflow;
import org.springframework.stereotype.Service;

@Service
public class TransferOrchestratorImpl implements TransferOrchestrator {

  private final TransferWorkflow workflow;

  public TransferOrchestratorImpl(TransferWorkflow workflow) {
    this.workflow = workflow;
  }

  @Override
  public OperationResult orchestrate(String opId, TransferRequest request) {
    validateRequest(request);
    var ctx = workflow.createContext(opId, request);
    workflow.execute(ctx);
    return workflow.toResult(ctx);
  }

  private void validateRequest(TransferRequest r) {
    if (r.amountMinor() <= 0) throw new IllegalArgumentException("amountMinor must be > 0");
    if (r.fromAccount().equals(r.toAccount())) throw new IllegalArgumentException("from/to must differ");
  }
}
