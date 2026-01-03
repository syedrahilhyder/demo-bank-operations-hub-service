package com.demo.bank.ops.orchestration.core;

import com.demo.bank.ops.api.dto.TransferRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransferContext {
  private final String opId;
  private final TransferRequest req;
  private final Map<String, Object> details = new HashMap<>();
  private String status = "OK";

  public TransferContext(String opId, TransferRequest req) {
    this.opId = opId;
    this.req = req;
    details.put("kind", "TRANSFER");
  }

  public String opId() { return opId; }
  public TransferRequest req() { return req; }
  public String status() { return status; }

  public void put(String k, Object v) { details.put(k, v); }
  public Object get(String k) { return details.get(k); }
  public Map<String, Object> details() { return Collections.unmodifiableMap(details); }

  public void fail(String status) { this.status = status; }
  public void succeed() { this.status = "OK"; }
}
