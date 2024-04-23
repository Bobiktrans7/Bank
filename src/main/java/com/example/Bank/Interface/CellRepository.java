package com.example.Bank.Interface;

import com.example.Bank.Entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {}
