package com.demo.bank.ops.pipeline.steps.impl;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidationStep implements OperationStep {
  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    if (ctx.kind() == null || ctx.kind().isBlank()) throw new IllegalArgumentException("kind required");
    meta.put("validatedKind", true);
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}
}
