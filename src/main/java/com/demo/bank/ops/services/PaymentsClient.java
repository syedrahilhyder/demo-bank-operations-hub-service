package com.demo.bank.ops.services;

public interface PaymentsClient {
  String local(String customerId, String from, String to, long amountMinor, String currency, String reference);
  String international(String customerId, String from, String iban, String bic, long amountMinor, String currency, String reference);
  String utility(String customerId, String from, String biller, String billRef, long amountMinor, String currency, String reference);
}
