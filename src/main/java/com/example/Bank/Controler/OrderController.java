package com.example.Bank.Controler;

import com.example.Bank.Entity.Order;
import com.example.Bank.Service.OrderService;
import com.example.Bank.Service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.processNewOrder(order);
        return ResponseEntity.ok("Заказ успешно создан и уведомление отправлено");
    }
}