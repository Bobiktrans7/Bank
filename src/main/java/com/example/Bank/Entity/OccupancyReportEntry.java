package com.example.Bank.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OccupancyReportEntry {
    private int cellNumber;
    private int shelfNumber;
    private LocalDate endDate;
}
