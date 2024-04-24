package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cells")
@Getter
@Setter
@NoArgsConstructor
public class Cell{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    @Column(nullable = false)
    private int cellNumber;


    public Cell(Shelf shelf, int cellNumber ) {
        this.shelf = shelf;
        this.cellNumber = cellNumber;
    }

    public void setOrder(Object o) {
    }
}
