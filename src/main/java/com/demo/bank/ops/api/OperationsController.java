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
  public OperationResult transfer(@Valid @RequestBody TransferRequest request) {
    return facade.executeTransfer(request);
  }

  @PostMapping("/card-auth-capture")
  public OperationResult cardAuthAndCapture(@Valid @RequestBody CardAuthAndCaptureRequest request) {
    return facade.executeCardAuthAndCapture(request);
  }
}
