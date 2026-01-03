package com.demo.bank.ops.pipeline.steps;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;

import java.util.Map;

public interface OperationStep {
  void before(PipelineContext ctx, Map<String, Object> meta);
  void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result);
}
