package com.example.Bank.Controler;

import com.example.Bank.Entity.Cell;
import com.example.Bank.Entity.Rack;
import com.example.Bank.Entity.Warehouse;
import com.example.Bank.Service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // Методы для работы со складами
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(createdWarehouse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return warehouse != null
                ? new ResponseEntity<>(warehouse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Методы для работы со стеллажами
    @PostMapping("/{warehouseId}/racks")
    public ResponseEntity<Rack> createRack(@PathVariable Long warehouseId, @RequestBody Rack rack) {
        Rack createdRack = warehouseService.createRack(warehouseId, rack);
        return createdRack != null
                ? new ResponseEntity<>(createdRack, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping("/racks/{id}")
    public ResponseEntity<Rack> getRackById(@PathVariable Long id) {
        Rack rack = warehouseService.getRackById(id);
        return rack != null
                ? new ResponseEntity<>(rack, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Методы для работы с ячейками
    @PostMapping("/racks/{rackId}/cells")
    public ResponseEntity<Cell> createCell(@PathVariable Long rackId, @RequestBody Cell cell) {
        Cell createdCell = warehouseService.createCell(rackId, cell);
        return createdCell != null
                ? new ResponseEntity<>(createdCell, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cells/{id}")
    public ResponseEntity<Cell> getCellById(@PathVariable Long id) {
        Cell cell = warehouseService.getCellById(id);
        return cell != null
                ? new ResponseEntity<>(cell, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
