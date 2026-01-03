package com.demo.bank.ops.orchestration.handlers;

import com.demo.bank.ops.api.dto.OperationResult;

public interface Handler<T> {
  OperationResult handle(String opId, T request);
}
