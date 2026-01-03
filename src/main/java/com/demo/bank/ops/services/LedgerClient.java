package com.demo.bank.ops.services;

public interface LedgerClient {
  String post(String paymentId, String debitAccount, String creditAccount, long amountMinor, String currency);
}
