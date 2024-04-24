package com.example.Bank.Controler;

import com.example.Bank.Entity.OccupancyReportEntry;
import com.example.Bank.Entity.OperationLogEntry;
import com.example.Bank.Entity.Order;
import com.example.Bank.Entity.OrderStatus;
import com.example.Bank.Interface.OrderRepository;
import com.example.Bank.Service.WarehouseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final OrderRepository orderRepository;

    public WarehouseController(WarehouseService warehouseService, OrderRepository orderRepository) {
        this.warehouseService = warehouseService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("shelves", warehouseService.getAllShelves());
        model.addAttribute("cells", warehouseService.getAllCells());
        model.addAttribute("orders", warehouseService.getAllOrders());
        return "home";
    }

    @GetMapping("/orders/recipient")
    public String getOrdersByRecipientPhone(@RequestParam("recipientPhoneNumber") String recipientPhoneNumber, Model model) {
        List<Order> orders = warehouseService.getOrdersByRecipientPhone(recipientPhoneNumber);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/pickup")
    public String pickupOrder(@RequestParam("orderId") Long orderId, Model model) {
        try {
            Order pickedUpOrder = warehouseService.pickupOrder(orderId);
            model.addAttribute("pickedUpOrder", pickedUpOrder);
            // Генерация документа о выдаче заказа
            // ...
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "orders";
    }

    @PostMapping("/shelves")
    public String createShelf(@RequestParam("shelfNumber") int shelfNumber) {
        warehouseService.createShelf(shelfNumber);
        return "redirect:/";
    }

    @PostMapping("/cells")
    public String createCell(@RequestParam("shelfNumber") int shelfNumber,
                             @RequestParam("cellNumber") int cellNumber) {
        warehouseService.createCell(shelfNumber, cellNumber);
        return "redirect:/";
    }

    @PostMapping("/orders")
    public String createOrder(@RequestParam("orderNumber") String orderNumber,
                              @RequestParam("shelfNumber") int shelfNumber,
                              @RequestParam("cellNumber") int cellNumber,
                              @RequestParam("recipientPhoneNumber") String recipientPhoneNumber,
                              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Model model) {
        try {
            warehouseService.createOrder(orderNumber, shelfNumber, cellNumber, startDate, endDate, recipientPhoneNumber);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "home";
        }
        return "redirect:/";
    }
    @GetMapping("/report")
    public String getWarehouseOccupancyReport(Model model) {
        List<OccupancyReportEntry> report = warehouseService.getWarehouseOccupancyReport();
        model.addAttribute("report", report);
        return "report";
    }

    @GetMapping("/report/expiration")
    public String getExpirationReport(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
        List<OperationLogEntry> report = warehouseService.getOperationReport(date);
        model.addAttribute("report", report);
        model.addAttribute("reportDate", date);
        return "report-expiration";
    }

    @PostMapping("/orders/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId, Model model) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            if (order.getStatus() == OrderStatus.RECEIVED) {
                order.setStatus(OrderStatus.ISSUED);
                orderRepository.save(order);
                model.addAttribute("successMessage", "Статус заказа обновлен");
            } else {
                model.addAttribute("errorMessage", "Заказ уже выдан");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("orders", warehouseService.getAllOrders());
        return "orders";
    }



}