package com.demo.bank.ops;

import com.demo.bank.ops.config.DemoServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(DemoServicesProperties.class)
public class OperationsHubApplication {
  public static void main(String[] args) {
    SpringApplication.run(OperationsHubApplication.class, args);
  }
}
