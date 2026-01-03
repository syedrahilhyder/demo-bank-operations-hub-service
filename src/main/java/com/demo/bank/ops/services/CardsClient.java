package com.demo.bank.ops.services;

public interface CardsClient { String authorize(String cardToken, long amountMinor, String currency); }
