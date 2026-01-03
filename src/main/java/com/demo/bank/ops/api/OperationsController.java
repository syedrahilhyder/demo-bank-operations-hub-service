package com.demo.bank.ops.api;

import com.demo.bank.ops.api.dto.*;
import com.demo.bank.ops.facade.OperationsFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ops")
public class OperationsController {

  private final OperationsFacade facade;

  public OperationsController(OperationsFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/transfer")
  public OperationResult transfer(@Valid @RequestBody TransferRequest req) { return facade.transfer(req); }

  @PostMapping("/international-transfer")
  public OperationResult intl(@Valid @RequestBody InternationalTransferRequest req) { return facade.internationalTransfer(req); }

  @PostMapping("/utility-pay")
  public OperationResult utility(@Valid @RequestBody UtilityPayRequest req) { return facade.utilityPay(req); }

  @PostMapping("/card-auth")
  public OperationResult cardAuth(@Valid @RequestBody CardAuthRequest req) { return facade.cardAuth(req); }

  @PostMapping("/transfer-async")
  public OperationResult transferAsync(@Valid @RequestBody TransferRequest req) { return facade.transferAsync(req); }

  @PostMapping("/healthcheck-deps")
  public OperationResult deps() { return facade.dependencyHealthcheck(); }
}
