package com.example.Bank.Controler;

import com.example.Bank.Entity.*;
import com.example.Bank.Interface.OrderRepository;
import com.example.Bank.Service.WarehouseService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final OrderRepository orderRepository;

    public WarehouseController(WarehouseService warehouseService, OrderRepository orderRepository) {
        this.warehouseService = warehouseService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order-report")
    public String getOrderReport(Model model) {
        List<OrderReport> orderReports = new ArrayList<>();

        // Получение списка заказов из базы данных или другого источника
        List<Order> orders = orderRepository.findAll();

        // Создание объектов OrderReport из списка заказов
        for (Order order : orders) {
            OrderReport orderReport = new OrderReport(order.getOrderNumber(), order.getRecipientPhoneNumber());
            orderReports.add(orderReport);
        }

        model.addAttribute("orderReport", orderReports);
        return "order-report";
    }
    @GetMapping("/daily-order-report")
    public String getDailyOrderReport(Model model) {
        Map<LocalDate, List<OrderReport>> orderReportsByDate = new HashMap<>();


        List<Order> orders = orderRepository.findAll();


        for (Order order : orders) {
            LocalDateTime startDateTime = order.getStartDate().atStartOfDay();
            LocalDate startDate = startDateTime.toLocalDate();
            OrderReport orderReport = new OrderReport(order.getOrderNumber(), order.getRecipientPhoneNumber());

            if (!orderReportsByDate.containsKey(startDate)) {
                orderReportsByDate.put(startDate, new ArrayList<>());
            }
            orderReportsByDate.get(startDate).add(orderReport);
        }

        model.addAttribute("orderReportsByDate", orderReportsByDate);
        return "daily-order-report";
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

    @GetMapping("/expired-orders")
    public String getExpiredOrders(Model model) {
        LocalDate currentDate = LocalDate.now();
        List<Order> expiredOrders = orderRepository.findAll().stream()
                .filter(order -> order.getEndDate().isBefore(currentDate))
                .collect(Collectors.toList());

        model.addAttribute("expiredOrders", expiredOrders);
        return "expired-orders";
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

    private void createExcelDocument(Order order) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Order Details");

        // Добавление данных в Excel файл
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("Order Number");
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(order.getOrderNumber());

        // Другие данные заказа можно добавить аналогично

        // Сохранение Excel файла
        String excelFilePath = "order_" + order.getOrderNumber() + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/orders")
    public String createOrder(@RequestParam("orderNumber") String orderNumber,
                              @RequestParam("shelfNumber") int shelfNumber,
                              @RequestParam("cellNumber") int cellNumber,
                              @RequestParam("recipientPhoneNumber") String recipientPhoneNumber,
                              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Model model) {
        try {
            Order order = warehouseService.createOrder(orderNumber, shelfNumber, cellNumber, startDate, endDate, recipientPhoneNumber);
            createExcelDocument(order);
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