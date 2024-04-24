package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelves")
@Getter
@Setter
@NoArgsConstructor
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int shelfNumber;

    @Getter
    @OneToMany(fetch = FetchType.EAGER)
    private List<Cell> cells = new ArrayList<>();


    public void addCell(Cell cell) {
        cells.add(cell);
        cell.setShelf(this);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
        cell.setShelf(null);
    }


    public Shelf(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

}
