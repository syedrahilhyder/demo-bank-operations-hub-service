package com.demo.bank.ops.api.dto;

import java.util.Map;

public record OperationResult(
    String operationId,
    String status,
    Map<String, Object> details
) {}
