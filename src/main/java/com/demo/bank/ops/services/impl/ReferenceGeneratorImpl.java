package com.demo.bank.ops.services.impl;

import com.demo.bank.ops.services.ReferenceGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ReferenceGeneratorImpl implements ReferenceGenerator {

  private final SecureRandom rnd = new SecureRandom();

  @Override
  public String generateRailReference(String operationId) {
    return "RAIL-" + operationId.substring(3, 11) + "-" + Integer.toHexString(rnd.nextInt());
  }
}
