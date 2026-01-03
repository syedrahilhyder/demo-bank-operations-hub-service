package com.demo.bank.ops.services;

public interface LedgerClient {
  void post(String paymentId, String debitAccount, String creditAccount, long amountMinor, String currency);
}
