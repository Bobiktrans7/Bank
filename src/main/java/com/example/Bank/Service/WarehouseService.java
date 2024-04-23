package com.example.Bank.Service;

import com.example.Bank.Entity.Cell;
import com.example.Bank.Entity.Rack;
import com.example.Bank.Entity.Warehouse;
import com.example.Bank.Interface.CellRepository;
import com.example.Bank.Interface.RackRepository;
import com.example.Bank.Interface.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final RackRepository rackRepository;
    private final CellRepository cellRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, RackRepository rackRepository, CellRepository cellRepository) {
        this.warehouseRepository = warehouseRepository;
        this.rackRepository = rackRepository;
        this.cellRepository = cellRepository;
    }

    // Методы для работы со складами
    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    // Методы для работы со стеллажами
    public Rack createRack(Long warehouseId, Rack rack) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse != null) {
            rack.setWarehouse(warehouse);
            return rackRepository.save(rack);
        }
        return null;
    }

    public Rack getRackById(Long id) {
        return rackRepository.findById(id).orElse(null);
    }

    // Методы для работы с ячейками
    public Cell createCell(Long rackId, Cell cell) {
        Rack rack = rackRepository.findById(rackId).orElse(null);
        if (rack != null) {
            cell.setRack(rack);
            return cellRepository.save(cell);
        }
        return null;
    }



    public Cell getCellById(Long id) {
        return cellRepository.findById(id).orElse(null);
    }
}