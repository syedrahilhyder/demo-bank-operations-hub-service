package com.demo.bank.ops.pipeline;

import java.util.HashMap;
import java.util.Map;

public class PipelineContext {
  private final String operationId;
  private final String kind;
  private final Map<String, Object> bag = new HashMap<>();

  public PipelineContext(String operationId, String kind) {
    this.operationId = operationId;
    this.kind = kind;
  }

  public String operationId() { return operationId; }
  public String kind() { return kind; }

  public void put(String key, Object value) { bag.put(key, value); }
  public Object get(String key) { return bag.get(key); }
}
