package com.demo.bank.ops.pipeline;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import com.demo.bank.ops.pipeline.steps.impl.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class OperationPipeline {

  private final List<OperationStep> steps;

  public OperationPipeline(CorrelationStep correlation, ValidationStep validation, EnrichmentStep enrichment, RiskStep risk, ObservabilityStep obs) {
    this.steps = List.of(correlation, validation, enrichment, risk, obs);
  }

  public OperationResult run(String opId, String kind, Supplier<OperationResult> core) {
    PipelineContext ctx = new PipelineContext(opId, kind);
    Map<String, Object> meta = new LinkedHashMap<>();
    meta.put("startedAt", Instant.now().toString());

    for (OperationStep s : steps) s.before(ctx, meta);

    OperationResult result;
    try {
      result = core.get();
      meta.put("coreStatus", "OK");
    } catch (Exception e) {
      meta.put("coreStatus", "FAILED");
      meta.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
      result = new OperationResult(opId, "FAILED", meta);
    }

    for (int i = steps.size() - 1; i >= 0; i--) steps.get(i).after(ctx, meta, result);
    meta.put("finishedAt", Instant.now().toString());

    return new OperationResult(result.operationId(), result.status(), meta);
  }
}
