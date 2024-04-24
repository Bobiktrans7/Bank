package com.example.Bank.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OperationLogEntry {
    private Order order;
    private String operationType;
    private LocalDateTime operationTime;
}
