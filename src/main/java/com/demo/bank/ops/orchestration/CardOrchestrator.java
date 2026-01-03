package com.demo.bank.ops.orchestration;

import com.demo.bank.ops.api.dto.CardAuthAndCaptureRequest;
import com.demo.bank.ops.api.dto.OperationResult;

public interface CardOrchestrator {
  OperationResult orchestrate(String opId, CardAuthAndCaptureRequest request);
}
