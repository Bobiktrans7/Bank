package com.example.Bank.Service;

import com.example.Bank.Entity.*;
import com.example.Bank.Interface.CellRepository;

import com.example.Bank.Interface.OperationLogRepository;
import com.example.Bank.Interface.OrderRepository;
import com.example.Bank.Interface.ShelfRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final ShelfRepository shelfRepository;
    private final CellRepository cellRepository;
    private final OrderRepository orderRepository;
    private final OperationLogRepository operationLogRepository;


    public WarehouseService(OrderRepository orderRepository, ShelfRepository shelfRepository,
                            CellRepository cellRepository, OperationLogRepository operationLogRepository) {
        this.orderRepository = orderRepository;
        this.shelfRepository = shelfRepository;
        this.cellRepository = cellRepository;
        this.operationLogRepository = operationLogRepository;
    }


    public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
    }

    public List<Cell> getAllCells() {
        return cellRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Shelf createShelf(int shelfNumber) {
        Optional<Shelf> existingShelf = shelfRepository.findByShelfNumber(shelfNumber);
        if (existingShelf.isPresent()) {
            throw new IllegalArgumentException("Shelf with number " + shelfNumber + " already exists.");
        }
        Shelf newShelf = new Shelf(shelfNumber);
        return shelfRepository.save(newShelf);
    }

    public Cell createCell(int shelfNumber, int cellNumber) {
        Shelf shelf = shelfRepository.findByShelfNumber(shelfNumber)
                .orElseThrow(() -> new IllegalArgumentException("Shelf with number " + shelfNumber + " does not exist."));
        Cell newCell = new Cell(shelf, cellNumber);
        return cellRepository.save(newCell);
    }
    public Order createOrder(String orderNumber, int shelfNumber, int cellNumber, LocalDate startDate, LocalDate endDate, String recipientPhoneNumber) {
        Shelf shelf = shelfRepository.findByShelfNumber(shelfNumber)
                .orElseThrow(() -> new IllegalArgumentException("Shelf with number " + shelfNumber + " does not exist."));
        Cell cell = cellRepository.findByShelf(shelf).stream()
                .filter(c -> c.getCellNumber() == cellNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cell with number " + cellNumber + " does not exist in shelf " + shelfNumber));

        checkCellAvailability(cell, startDate, endDate);
        checkFreeSpaceAvailability();

        Order existingOrder = orderRepository.findByOrderNumber(orderNumber).orElse(null);
        if (existingOrder != null) {
            throw new IllegalArgumentException("Order with number " + orderNumber + " already exists.");
        }

        Order order = new Order(orderNumber, shelf, cell, startDate, endDate, recipientPhoneNumber, OrderStatus.RECEIVED);
        order = orderRepository.save(order); // Сохраняем заказ в базе данных

        OperationLog operationLog = new OperationLog(order, "RECEIVED");
        operationLogRepository.save(operationLog);

        return order;
    }

    private void checkCellAvailability(Cell cell, LocalDate startDate, LocalDate endDate) {
        List<Order> ordersInCell = orderRepository.findByCell(cell);
        if (!ordersInCell.isEmpty()) {
            throw new IllegalArgumentException("Cell " + cell.getCellNumber() + " in shelf " + cell.getShelf().getShelfNumber() + " already has an order.");
        }
        if (ordersInCell.stream().anyMatch(o -> o.getStartDate().isBefore(endDate) && o.getEndDate().isAfter(startDate))) {
            throw new IllegalArgumentException("Cell " + cell.getCellNumber() + " in shelf " + cell.getShelf().getShelfNumber() + " is occupied during the specified period.");
        }
    }


    private void checkFreeSpaceAvailability() {
        List<Cell> allCells = cellRepository.findAll().stream().flatMap(cell -> cell.getShelf().getCells().stream()).collect(Collectors.toList());
        List<Cell> occupiedCells = orderRepository.findAll().stream().map(Order::getCell).distinct().collect(Collectors.toList());
        List<Cell> availableCells = allCells.stream().filter(cell -> !occupiedCells.contains(cell)).collect(Collectors.toList());


    }

    public List<Order> getOrdersByRecipientPhone(String recipientPhoneNumber) {
        return orderRepository.findByRecipientPhoneNumber(recipientPhoneNumber);
    }


    public List<OccupancyReportEntry> getWarehouseOccupancyReport() {
        List<Order> orders = orderRepository.findAll();
        List<OccupancyReportEntry> report = new ArrayList<>();

        for (Order order : orders) {
            Cell cell = order.getCell();
            Shelf shelf = cell.getShelf();
            report.add(new OccupancyReportEntry(cell.getCellNumber(), shelf.getShelfNumber(), order.getEndDate()));
        }

        report.sort(Comparator.comparing(OccupancyReportEntry::getEndDate));
        return report;
    }



    public Order pickupOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Освобождение соответствующей ячейки склада
        Cell cell = order.getCell();
        cell.setOrder(null);
        cellRepository.save(cell);

        // Формирование документа о выдаче заказа
        // ...

        OperationLog operationLog = new OperationLog(order, "ISSUED");
        operationLogRepository.save(operationLog);

        return order;
    }
    public List<Order> getOrdersByExpirationDate(LocalDate date) {
        LocalDate nextDay = date.plusDays(1);
        return orderRepository.findByEndDateBetween(date, nextDay);
    }

    public List<OperationLogEntry> getOperationReport(LocalDate date) {
        List<Order> orders = getOrdersByExpirationDate(date);
        List<OperationLogEntry> report = new ArrayList<>();

        for (Order order : orders) {
            String operationType;
            if (order.getEndDate().isBefore(LocalDate.now())) {
                operationType = "EXPIRED";
            } else if (order.getEndDate().isEqual(LocalDate.now())) {
                operationType = "EXPIRING";
            } else {
                operationType = "RECEIVED";
            }

            OperationLog operationLog = new OperationLog(order, operationType);
            report.add(new OperationLogEntry(order, operationType, operationLog.getOperationTime()));
        }

        return report;
    }






}
