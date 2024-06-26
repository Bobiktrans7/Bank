package com.example.Bank.Interface;

import com.example.Bank.Entity.Cell;
import com.example.Bank.Entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findByShelf(Shelf shelf);
}
