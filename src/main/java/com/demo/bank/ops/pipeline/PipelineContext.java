package com.demo.bank.ops.pipeline;

import java.util.HashMap;
import java.util.Map;

public class PipelineContext {
  private final String opId;
  private final String kind;
  private final Map<String, Object> bag = new HashMap<>();

  public PipelineContext(String opId, String kind) {
    this.opId = opId;
    this.kind = kind;
  }

  public String opId() { return opId; }
  public String kind() { return kind; }

  public void put(String k, Object v) { bag.put(k, v); }
  public Object get(String k) { return bag.get(k); }
}
