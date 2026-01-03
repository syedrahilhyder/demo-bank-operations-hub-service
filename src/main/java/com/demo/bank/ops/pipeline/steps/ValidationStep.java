package com.demo.bank.ops.pipeline.steps;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.pipeline.PipelineContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidationStep implements OperationStep {

  @Override
  public void before(PipelineContext ctx, Map<String, Object> meta) {
    validateKind(ctx.kind());
    meta.put("validatedKind", ctx.kind());
  }

  @Override
  public void after(PipelineContext ctx, Map<String, Object> meta, OperationResult result) {}

  private void validateKind(String kind) {
    if (kind == null || kind.isBlank()) throw new IllegalArgumentException("kind is required");
    if (kind.length() > 64) throw new IllegalArgumentException("kind too long");
    validateAllowed(kind);
  }

  private void validateAllowed(String kind) {
    if (!kind.equals("TRANSFER") && !kind.equals("CARD_AUTH_CAPTURE")) {
      throw new IllegalArgumentException("unsupported kind: " + kind);
    }
  }
}
