package com.demo.bank.ops.orchestration;

import com.demo.bank.ops.api.dto.CardAuthAndCaptureRequest;
import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.orchestration.core.CardWorkflow;
import org.springframework.stereotype.Service;

@Service
public class CardOrchestratorImpl implements CardOrchestrator {

  private final CardWorkflow workflow;

  public CardOrchestratorImpl(CardWorkflow workflow) {
    this.workflow = workflow;
  }

  @Override
  public OperationResult orchestrate(String opId, CardAuthAndCaptureRequest request) {
    var ctx = workflow.createContext(opId, request);
    workflow.execute(ctx);
    return workflow.toResult(ctx);
  }
}
