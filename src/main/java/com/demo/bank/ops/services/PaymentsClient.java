package com.demo.bank.ops.services;

public interface PaymentsClient {
  String initiateLocal(String customerId, String fromAccount, String toAccount, long amountMinor, String currency);
}
