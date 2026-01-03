package com.demo.bank.ops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OperationsHubApplication {
  public static void main(String[] args) {
    SpringApplication.run(OperationsHubApplication.class, args);
  }
}
