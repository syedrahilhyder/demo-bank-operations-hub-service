package com.demo.bank.ops.pipeline.steps;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class EnrichmentStep implements OperationStep {

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    String env = resolveEnvironment();
    ctx.put("env", env);
    meta.put("env", env);
    meta.put("enrichedAt", Instant.now().toString());
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}

  @Cacheable(cacheNames = "env-cache")
  public String resolveEnvironment() {
    return "LOCAL-DEMO";
  }
}
