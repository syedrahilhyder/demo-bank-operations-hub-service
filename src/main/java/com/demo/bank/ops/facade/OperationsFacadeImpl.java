package com.demo.bank.ops.facade;

import com.demo.bank.ops.api.dto.*;
import com.demo.bank.ops.orchestration.OrchestrationRouter;
import com.demo.bank.ops.pipeline.OperationPipeline;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OperationsFacadeImpl implements OperationsFacade {

  private final OperationPipeline pipeline;
  private final OrchestrationRouter router;

  public OperationsFacadeImpl(OperationPipeline pipeline, OrchestrationRouter router) {
    this.pipeline = pipeline;
    this.router = router;
  }

  @Override
  public OperationResult transfer(TransferRequest req) {
    String opId = newOp();
    return pipeline.run(opId, "TRANSFER", () -> router.routeTransfer(opId, req));
  }

  @Override
  public OperationResult internationalTransfer(InternationalTransferRequest req) {
    String opId = newOp();
    return pipeline.run(opId, "INTERNATIONAL_TRANSFER", () -> router.routeInternational(opId, req));
  }

  @Override
  public OperationResult utilityPay(UtilityPayRequest req) {
    String opId = newOp();
    return pipeline.run(opId, "UTILITY_PAY", () -> router.routeUtility(opId, req));
  }

  @Override
  public OperationResult cardAuth(CardAuthRequest req) {
    String opId = newOp();
    return pipeline.run(opId, "CARD_AUTH", () -> router.routeCard(opId, req));
  }

  @Override
  public OperationResult transferAsync(TransferRequest req) {
    String opId = newOp();
    fireAndForget(opId, req);
    return new OperationResult(opId, "ACCEPTED", Map.of("mode", "ASYNC"));
  }

  @Async
  public CompletableFuture<Void> fireAndForget(String opId, TransferRequest req) {
    pipeline.run(opId, "TRANSFER_ASYNC", () -> router.routeTransfer(opId, req));
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public OperationResult dependencyHealthcheck() {
    String opId = newOp();
    return pipeline.run(opId, "DEPS_HEALTHCHECK", () -> router.routeDeps(opId));
  }

  private String newOp() { return "OP-" + UUID.randomUUID(); }
}
