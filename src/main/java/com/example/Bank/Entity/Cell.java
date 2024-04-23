package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cells")
@Getter
@Setter
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "rack_id", nullable = false)
    private Rack rack;

    // Конструкторы, геттеры и сеттеры
}
