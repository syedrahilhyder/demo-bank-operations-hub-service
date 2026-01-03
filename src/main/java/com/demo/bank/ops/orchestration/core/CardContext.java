package com.demo.bank.ops.orchestration.core;

import com.demo.bank.ops.api.dto.CardAuthAndCaptureRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CardContext {
  private final String opId;
  private final CardAuthAndCaptureRequest req;
  private final Map<String, Object> details = new HashMap<>();
  private String status = "OK";

  public CardContext(String opId, CardAuthAndCaptureRequest req) {
    this.opId = opId;
    this.req = req;
    details.put("kind", "CARD_AUTH_CAPTURE");
  }

  public String opId() { return opId; }
  public CardAuthAndCaptureRequest req() { return req; }
  public String status() { return status; }

  public void put(String k, Object v) { details.put(k, v); }
  public Object get(String k) { return details.get(k); }
  public Map<String, Object> details() { return Collections.unmodifiableMap(details); }

  public void fail(String status) { this.status = status; }
  public void succeed() { this.status = "OK"; }
}
