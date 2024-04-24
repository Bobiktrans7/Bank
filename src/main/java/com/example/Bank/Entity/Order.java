package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @Column
    private String recipientPhoneNumber;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.RECEIVED;

    public Order(String orderNumber, Shelf shelf, Cell cell, LocalDate startDate, LocalDate endDate, String recipient_phone_number, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.shelf = shelf;
        this.cell = cell;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recipientPhoneNumber = recipient_phone_number;
        this.status = status;

    }


}