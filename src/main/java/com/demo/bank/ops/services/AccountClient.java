package com.demo.bank.ops.services;

import java.util.UUID;

public interface AccountClient {
  void debit(String accountId, long amountMinor);
  void credit(String accountId, long amountMinor);
  UUID hold(String accountId, long amountMinor, String reason);
  void release(UUID holdId);
}
