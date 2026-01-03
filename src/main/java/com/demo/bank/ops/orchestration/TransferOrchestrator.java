package com.demo.bank.ops.orchestration;

import com.demo.bank.ops.api.dto.OperationResult;
import com.demo.bank.ops.api.dto.TransferRequest;

public interface TransferOrchestrator {
  OperationResult orchestrate(String opId, TransferRequest request);
}
