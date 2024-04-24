package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation_log")
@Getter
@Setter
@NoArgsConstructor
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "operation_type", nullable = false, length = 10)
    private String operationType;

    @Column(name = "operation_time", nullable = false)
    private LocalDateTime operationTime;

    public OperationLog(Order order, String operationType) {
        this.order = order;
        this.operationType = operationType;
        this.operationTime = LocalDateTime.now();
    }
}
