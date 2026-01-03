package com.demo.bank.ops.pipeline.steps;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class CorrelationStep implements OperationStep {

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    String correlationId = "CORR-" + UUID.randomUUID();
    ctx.put("correlationId", correlationId);
    meta.put("correlationId", correlationId);
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}
}
