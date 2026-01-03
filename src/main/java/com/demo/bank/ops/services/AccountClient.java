package com.demo.bank.ops.services;

public interface AccountClient {
  void debit(String accountId, long amountMinor);
  void credit(String accountId, long amountMinor);
}
