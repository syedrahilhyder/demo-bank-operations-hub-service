package com.demo.bank.ops.services;

public interface LimitsClient {
  boolean check(String customerId, long amountMinor);
}
