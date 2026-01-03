package com.demo.bank.ops.orchestration.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Compensation {
  private static final Logger log = LoggerFactory.getLogger(Compensation.class);

  public void runAll(List<Runnable> actions) {
    List<Runnable> reversed = new ArrayList<>(actions);
    for (int i = reversed.size() - 1; i >= 0; i--) {
      try {
        reversed.get(i).run();
      } catch (Exception e) {
        log.warn("compensation.failed err={}", e.toString());
      }
    }
  }
}
