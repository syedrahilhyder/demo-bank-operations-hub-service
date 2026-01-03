package com.demo.bank.ops.facade;

import com.demo.bank.ops.api.dto.*;

public interface OperationsFacade {
  OperationResult executeTransfer(TransferRequest request);
  OperationResult executeCardAuthAndCapture(CardAuthAndCaptureRequest request);
}
