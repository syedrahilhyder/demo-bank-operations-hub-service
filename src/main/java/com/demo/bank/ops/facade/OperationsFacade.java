package com.demo.bank.ops.facade;

import com.demo.bank.ops.api.dto.*;

public interface OperationsFacade {
  OperationResult transfer(TransferRequest req);
  OperationResult internationalTransfer(InternationalTransferRequest req);
  OperationResult utilityPay(UtilityPayRequest req);
  OperationResult cardAuth(CardAuthRequest req);
  OperationResult transferAsync(TransferRequest req);
  OperationResult dependencyHealthcheck();
}
