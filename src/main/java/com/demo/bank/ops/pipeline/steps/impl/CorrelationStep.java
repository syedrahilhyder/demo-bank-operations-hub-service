package com.demo.bank.ops.pipeline.steps.impl;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class CorrelationStep implements OperationStep {
  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    String corr = "CORR-" + UUID.randomUUID();
    ctx.put("corr", corr);
    meta.put("corr", corr);
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}
}
