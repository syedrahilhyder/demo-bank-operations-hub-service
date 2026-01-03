package com.demo.bank.ops.facade;

import com.demo.bank.ops.api.dto.*;
import com.demo.bank.ops.orchestration.*;
import com.demo.bank.ops.pipeline.OperationPipeline;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OperationsFacadeImpl implements OperationsFacade {

  private final TransferOrchestrator transferOrchestrator;
  private final CardOrchestrator cardOrchestrator;
  private final OperationPipeline pipeline;

  public OperationsFacadeImpl(
      TransferOrchestrator transferOrchestrator,
      CardOrchestrator cardOrchestrator,
      OperationPipeline pipeline
  ) {
    this.transferOrchestrator = transferOrchestrator;
    this.cardOrchestrator = cardOrchestrator;
    this.pipeline = pipeline;
  }

  @Override
  public OperationResult executeTransfer(TransferRequest request) {
    String opId = "OP-" + UUID.randomUUID();
    return pipeline.run(opId, "TRANSFER", () -> transferOrchestrator.orchestrate(opId, request));
  }

  @Override
  public OperationResult executeCardAuthAndCapture(CardAuthAndCaptureRequest request) {
    String opId = "OP-" + UUID.randomUUID();
    return pipeline.run(opId, "CARD_AUTH_CAPTURE", () -> cardOrchestrator.orchestrate(opId, request));
  }
}
