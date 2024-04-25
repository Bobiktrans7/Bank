package com.example.Bank.Interface;

import com.example.Bank.Entity.Cell;
import com.example.Bank.Entity.Order;
import com.example.Bank.Entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByShelfAndCell(Shelf shelf, Cell cell);
    List<Order> findByCell(Cell cell);
    List<Order> findByRecipientPhoneNumber(String recipientPhoneNumber);
    List<Order> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT o FROM Order o WHERE o.endDate <= :currentDate")
    List<Order> findOrdersWithExpiringStorage(LocalDate currentDate);

}

