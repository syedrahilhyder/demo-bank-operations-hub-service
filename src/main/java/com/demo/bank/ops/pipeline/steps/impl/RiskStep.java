package com.demo.bank.ops.pipeline.steps.impl;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RiskStep implements OperationStep {

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    int score = Math.min(99, Math.abs(ctx.kind().hashCode() % 100));
    meta.put("riskScore", score);
    meta.put("riskBand", score < 30 ? "LOW" : (score < 70 ? "MEDIUM" : "HIGH"));
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}
}
