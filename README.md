# demo-bank-operations-hub-service

A deliberately **complex** orchestration hub service to stress-test SenfoAI call-graphs and diagrams.

What it does:
- Exposes multiple orchestration APIs (transfer, card+payment)
- Implements deep internal call chains (facade -> orchestrator -> pipeline -> validators -> enrichers -> workflows -> connectors)
- Calls existing demo-bank services over HTTP:
  - payments-service (8080)
  - account-service (8081)
  - ledger-service (8082)
  - limits-service (8083)
  - cards-service (8088)
- Uses:
  - Spring Boot 3 / Java 21
  - Resilience4j (retry + circuit breaker)
  - Cache abstraction
  - Actuator
  - Validation
  - OpenAPI via springdoc

## Run
Start other demo-bank services first, then:
```bash
mvn clean install
mvn spring-boot:run
```

## Try
```bash
curl -X POST "http://localhost:8090/ops/transfer"   -H "Content-Type: application/json"   -d '{"customerId":"C1","fromAccount":"A1","toAccount":"A2","amountMinor":1000,"currency":"AED","reference":"demo"}'
```
