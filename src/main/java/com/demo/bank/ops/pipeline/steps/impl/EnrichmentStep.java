package com.demo.bank.ops.pipeline.steps.impl;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EnrichmentStep implements OperationStep {

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    meta.put("env", env());
    meta.put("tenant", tenant(ctx.kind()));
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}

  @Cacheable(cacheNames = "env-cache")
  public String env() { return "LOCAL-DEMO"; }

  private String tenant(String kind) {
    return kind.contains("INTERNATIONAL") ? "TENANT-INTL" : "TENANT-LOCAL";
  }
}
