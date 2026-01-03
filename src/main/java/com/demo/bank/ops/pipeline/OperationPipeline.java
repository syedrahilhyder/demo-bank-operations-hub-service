package com.demo.bank.ops.pipeline;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.steps.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class OperationPipeline {

  private final List<OperationStep> steps;

  public OperationPipeline(
      CorrelationStep correlationStep,
      ValidationStep validationStep,
      EnrichmentStep enrichmentStep,
      ObservabilityStep observabilityStep
  ) {
    this.steps = List.of(correlationStep, validationStep, enrichmentStep, observabilityStep);
  }

  public OperationResult run(String operationId, String kind, Supplier<OperationResult> core) {
    PipelineContext ctx = new PipelineContext(operationId, kind);
    Map<String, Object> meta = new LinkedHashMap<>();
    meta.put("startedAt", Instant.now().toString());

    for (OperationStep s : steps) {
      s.before(ctx, meta);
    }

    OperationResult result;
    try {
      result = core.get();
      meta.put("coreStatus", "OK");
    } catch (Exception e) {
      meta.put("coreStatus", "FAILED");
      meta.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
      result = new OperationResult(operationId, "FAILED", meta);
    }

    for (int i = steps.size() - 1; i >= 0; i--) {
      steps.get(i).after(ctx, meta, result);
    }

    meta.put("finishedAt", Instant.now().toString());
    return new OperationResult(result.operationId(), result.status(), meta);
  }
}
