package com.demo.bank.ops.orchestration;

import com.demo.bank.ops.api.dto.*;
import com.demo.bank.ops.orchestration.handlers.*;
import org.springframework.stereotype.Component;

@Component
public class OrchestrationRouter {

  private final TransferHandler transfer;
  private final InternationalHandler international;
  private final UtilityHandler utility;
  private final CardHandler card;
  private final DepsHealthHandler deps;

  public OrchestrationRouter(TransferHandler transfer, InternationalHandler international, UtilityHandler utility, CardHandler card, DepsHealthHandler deps) {
    this.transfer = transfer;
    this.international = international;
    this.utility = utility;
    this.card = card;
    this.deps = deps;
  }

  public OperationResult routeTransfer(String opId, TransferRequest req) { return transfer.handle(opId, req); }
  public OperationResult routeInternational(String opId, InternationalTransferRequest req) { return international.handle(opId, req); }
  public OperationResult routeUtility(String opId, UtilityPayRequest req) { return utility.handle(opId, req); }
  public OperationResult routeCard(String opId, CardAuthRequest req) { return card.handle(opId, req); }
  public OperationResult routeDeps(String opId) { return deps.handle(opId); }
}
