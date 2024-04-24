package com.example.Bank.Interface;

import com.example.Bank.Entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    List<OperationLog> findByOperationTimeBetween(LocalDateTime start, LocalDateTime end);
}