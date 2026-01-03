package com.demo.bank.ops.pipeline.steps.impl;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import com.demo.bank.ops.pipeline.steps.OperationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ObservabilityStep implements OperationStep {

  private static final Logger log = LoggerFactory.getLogger(ObservabilityStep.class);

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    log.info("op.start opId={} kind={} corr={}", ctx.opId(), ctx.kind(), meta.get("corr"));
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {
    log.info("op.end opId={} status={}", ctx.opId(), result.status());
  }
}
